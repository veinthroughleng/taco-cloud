import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart/cart-service';
import {AuthService} from "../login/auth-service";

@Component({
  selector: 'taco-header',
  templateUrl: 'header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {
  cart: CartService;

  constructor(cart: CartService, private authService: AuthService) {
    this.cart = cart;
  }

  ngOnInit() { }

  get isLogged() {
    return this.authService.isLogged();
  }

  get loggedUser() {
    return this.authService.getLoggedUser();
  }
}
