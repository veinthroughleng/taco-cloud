import {Component, OnInit, Injectable} from '@angular/core';
import {CartService} from './cart-service';
import {HttpClient} from '@angular/common/http';
import {Constants} from "../utils/Constants";

@Component({
  selector: 'taco-cart',
  templateUrl: 'cart.component.html',
  styleUrls: ['./cart.component.css']
})

@Injectable()
export class CartComponent implements OnInit {

  model = {
    name: '',
    street: '',
    city: '',
    state: '',
    zip: '',
    ccNumber: '',
    ccExpiration: '',
    ccCVV: '',
    tacos: []
  };

  constructor(private cart: CartService, private httpClient: HttpClient) {
    this.cart = cart;
  }

  ngOnInit() {
  }

  get cartItems() {
    return this.cart.getItemsInCart();
  }

  get cartTotal() {
    return this.cart.getCartTotal();
  }

  onSubmit() {
    // this.model.tacos = this.cart.getItemsInCart();
    this.cart.getItemsInCart().forEach((cartItem: any) => {
      // push url rather than taco
      // this.model.tacos.push(cartItem.taco);
      this.model.tacos.push(cartItem.taco._links.self.href);
    });

    this.httpClient.post(
      Constants.REST_URL_BASIS + 'orders', this.model)
      .subscribe(r => this.cart.emptyCart());

    // TODO: Do something after this...navigate to a thank you page or something
  }

}
