import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {OAuthService} from "angular-oauth2-oidc";
import {authPasswordFlowConfig} from "./auth-password-flow.config";
import {Constants} from "../utils/Constants";
import {CartService} from '../cart/cart-service';

@Injectable()
export class AuthService {

  constructor(private _http: HttpClient, private _router: Router,
              private oAuthService: OAuthService, private cart: CartService) {
    this.oAuthService.setStorage(localStorage);
    this.oAuthService.configure(authPasswordFlowConfig);
  }

  getOAuthService() {
    return this.oAuthService;
  }

  isLogged() {
    return this.oAuthService.getAccessToken() !== null &&
      this.oAuthService.hasValidAccessToken();

  }

  increaseAndGetErrorCount() {
    let errorCount = localStorage.getItem("error count") ?
      Number(localStorage.getItem("error count")) + 1 : 1;
    localStorage.setItem("error count", errorCount.toString());
    return errorCount;
  }

  deleteErrorCount() {
    return localStorage.removeItem("error count");
  }

  setLoggedUser(user) {
    localStorage.setItem("user_logged", user);
  }

  deleteLoggedUser() {
    localStorage.removeItem("user_logged");
  }

  getLoggedUser() {
    return localStorage.getItem("user_logged");
  }

  checkCredentials() {
    if (!this.isLogged()) {
      this._router.navigate(['login']);
    }
  }

  getOrders(): Observable<any> {
    return this.getResource(Constants.URL_REST_ORDERS);
  }

  getUserInfo(): Observable<any> {
    return this.getResource(Constants.URL_REST_USERINFO);
  }

  postTaco(taco): Observable<any> {
    return this.postResource(Constants.URL_REST_TACOS, taco);
  }

  postOrder(order): Observable<any> {
    return this.postResource(Constants.URL_REST_ORDERS, order);
  }

  getResource(resourceUrl): Observable<any> {
    let params = new HttpParams().set('access_token', this.oAuthService.getAccessToken());
    return this._http.get(resourceUrl,
      {params})
      .catch(
        (error: any) => {
          // If server restarted, the token will be invalid, which make rest call failed;
          // But the token in localStorage make isLogged() return true.
          // Then if error count exceed the threshold, it should re-login.
          if (this.increaseAndGetErrorCount() >= Constants.THRESHOLD_REST_ERROR) {
            this.logout();
            this._router.navigate(['login']);
          }
          return Observable.throw(error.json().error || 'Server error');
        });

    //   let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8')
    //     .set('Authorization', 'Bearer ' + this.oAuthService.getAccessToken());
    //   return this._http.get(Constants.URL_REST_ORDERS,
    //     {headers});
  }

  postResource(resourceUrl, body): Observable<any> {
    let params = new HttpParams().set('access_token', this.oAuthService.getAccessToken());
    return this._http.post(resourceUrl, body, {params})
      .catch(
        (error: any) => {
          // If server restarted, the token will be invalid, which make rest call failed;
          // But the token in localStorage make isLogged() return true.
          // Then if error count exceed the threshold, it should re-login.
          if (this.increaseAndGetErrorCount() >= Constants.THRESHOLD_REST_ERROR) {
            this.logout();
            this._router.navigate(['login']);
          }
          return Observable.throw(error.json().error || 'Server error');
        });

  }

  logout() {
    this.oAuthService.logOut(true);
    this.deleteErrorCount();
    this.deleteLoggedUser();
    this.cart.emptyCart();
    location.reload();
  }
}
