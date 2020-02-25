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
    this.cart.getItemsInCart().forEach((cartItem: any) => {
        // prototype version
        this.model.tacos.push(cartItem.taco);

        // href version
        // push url rather than taco
        // this.model.tacos.push(cartItem.taco._links.self.href);
      }
    );

    this.httpClient.post(
      Constants.REST_URL_ORDER, this.model)
      .subscribe(r => this.cart.emptyCart());

    // TODO: Do something after this...navigate to a thank you page or something
  }

}
