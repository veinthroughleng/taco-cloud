import {HttpHeaders} from "@angular/common/http";

export class Constants {
  static HOST = '192.168.1.103';
  static PORT = 8080;
  static URL_BASIS = 'http://' + Constants.HOST +':' + Constants.PORT + '/';
  static REST_URL_BASIS = Constants.URL_BASIS + 'rest/';
  
  static JSON = 'application/json';
  static JSON_HEADER = new HttpHeaders({ 'Content-Type': Constants.JSON });
}
