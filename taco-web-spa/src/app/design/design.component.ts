import {Component, OnInit, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router/';
import {CartService} from '../cart/cart-service';
import {AuthService} from '../login/auth-service';
import {Constants} from "../utils/Constants";

@Component({
  selector: 'taco-design',
  templateUrl: 'design.component.html',
  styleUrls: ['./design.component.css']
})

@Injectable()
export class DesignComponent implements OnInit {

  model = {
    name: '',
    ingredients: []
  };

  allIngredients: any;
  wraps = [];
  proteins = [];
  veggies = [];
  cheeses = [];
  sauces = [];

  constructor(private httpClient: HttpClient, private router: Router,
              private authService: AuthService, private cart: CartService) {
  }

  // tag::ngOnInit[]
  ngOnInit() {
    this.authService.checkCredentials();

    this.httpClient.get(Constants.URL_REST_INGREDIENTS)
      .subscribe((data: any) => {
        // prototype version
        this.allIngredients = data;

        // href version
        // this.allIngredients = data._embedded.ingredients;
        // this.allIngredients.forEach((ingredient: any) => {
        //     //add id attribute
        //     ingredient.id =
        //       ingredient._links.self.href.split("/").reverse()[0];
        //     //delete ingredient._links;
        //   }
        // );
        this.wraps = this.allIngredients.filter(w => w.type === 'WRAP');
        this.proteins = this.allIngredients.filter(p => p.type === 'PROTEIN');
        this.veggies = this.allIngredients.filter(v => v.type === 'VEGGIES');
        this.cheeses = this.allIngredients.filter(c => c.type === 'CHEESE');
        this.sauces = this.allIngredients.filter(s => s.type === 'SAUCE');
      });
  }

  // end::ngOnInit[]

  updateIngredients(ingredient, event) {
    if (event.target.checked) {
      this.model.ingredients.push(ingredient);
    } else {
      this.model.ingredients.splice(this.model.ingredients.findIndex(i => i === ingredient), 1);
    }
  }

  // tag::onSubmit[]
  header = Constants.JSON_HEADER;
  options = {headers: this.header};

  onSubmit() {
    // href version
    // map to every ingredient to href
    // this.model.ingredients =
    //   this.model.ingredients.map(ingredient => ingredient._links.self.href);

    this.authService.postTaco(this.model)
      .subscribe(
        taco =>
          this.cart.addToCart(taco));

    this.router.navigate(['/cart']);
  }

  // end::onSubmit[]

}
