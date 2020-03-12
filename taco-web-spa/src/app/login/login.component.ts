import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {OAuthService} from "angular-oauth2-oidc";
import {authPasswordFlowConfig} from "./auth-password-flow.config";
import {Observable} from "rxjs";
import {AuthService} from "./auth-service";

@Component({
  selector: 'login-taco',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  userName: string;
  password: string;
  loginFailed: boolean = false;
  userProfile: object;
  oAuthService: OAuthService;

  constructor(
    private _router: Router, private _http: HttpClient, private authService: AuthService) {
    this.oAuthService = authService.getOAuthService();
    // this.oauthService.loadDiscoveryDocument();
    // this.oauthService.tryLogin({});
  }

  ngOnInit() {
  }

  get access_token() {
    return this.oAuthService.getAccessToken();
  }

  get access_token_expiration() {
    return this.oAuthService.getAccessTokenExpiration();
  }

  get isLogged() {
    return this.authService.isLogged();
  }

  loadUserProfile() {
    let profile = null;
    this.oAuthService.loadUserProfile()
      .then(up =>
        profile = up
      );
    return profile;
  }

  loginWithPassword() {
    // this.oauthService.setupAutomaticSilentRefresh();
    // this.oauthService.initImplicitFlow();
    this.oAuthService
      .fetchTokenUsingPasswordFlow(
        this.userName,
        this.password
      )
      .then((tokenInfo: any) => {
        console.debug('successfully logged in');
        this.authService.setLoggedUser(this.userName);
        this.loginFailed = false;
        // return this.oauthService.loadUserProfile()
        //   .then((userInfo: any) => {
        //     if (userInfo) {
        //       this.userProfile = userInfo;
        //     }
        //   });
      })
      .catch(err => {
        console.error('error logging in', err);
        this.loginFailed = true;
      });
  }

  get loggedUser() {
    return this.authService.getLoggedUser();
  }

  logout() {
    this.authService.logout();
  }

}
