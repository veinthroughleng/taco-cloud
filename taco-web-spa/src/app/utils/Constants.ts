import {HttpHeaders} from "@angular/common/http";

export class Constants {
  static HOST = '192.168.1.103';
  static PORT = 8080;
  static URL_BASIS = 'http://' + Constants.HOST +':' + Constants.PORT;
  static REST_PREFIX = '';
  static REST_URL_BASIS = Constants.URL_BASIS + Constants.REST_PREFIX;
  static REST_URL_ORDER = Constants.REST_URL_BASIS + "/orders";
  static REST_URL_INGREDIENT = Constants.REST_URL_BASIS + "/ingredients";
  static REST_URL_TACO = Constants.REST_URL_BASIS + "/tacos";
  static REST_URL_RECENT = Constants.REST_URL_TACO + "/recent";
  
  static JSON = 'application/json';
  static JSON_HEADER = new HttpHeaders({ 'Content-Type': Constants.JSON });
}
