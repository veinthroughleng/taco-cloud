import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../login/auth-service";

@Component({
  selector: 'taco-user',
  templateUrl: 'user.component.html',
  // styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {
  userInfo: object;
  orders: object[];

  constructor(
    private _router: Router, private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.checkCredentials();

    this.authService.getOrders().subscribe(
      orders => {
        this.orders = orders;
        if (this.orders.length != 0) {
          this.userInfo = orders[0]['user'];
        } else {
          this.authService.getUserInfo().subscribe(
            user =>
              this.userInfo = user)
        }
      });
    // this.userInfo = this.authService.getResource(Constants.URL_REST_USERS);
  }

  get loggedUser() {
    return this.authService.getLoggedUser();
  }

  logout() {
    this.authService.logout();
  }
}
