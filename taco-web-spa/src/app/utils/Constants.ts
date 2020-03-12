import {HttpHeaders} from '@angular/common/http';

export class Constants {
  // resource
  static REST_HOST = '192.168.1.103';
  static REST_PORT = 8080;
  static RESOURCE_PREFIX = '/rest';
  static URL_REST_BASIS = 'http://' + Constants.REST_HOST + ':' + Constants.REST_PORT;
  static URL_RESOURCE_BASIS = Constants.URL_REST_BASIS + Constants.RESOURCE_PREFIX;
  static URL_REST_ORDERS = Constants.URL_RESOURCE_BASIS + '/orders';
  static URL_REST_USERINFO = Constants.URL_RESOURCE_BASIS + '/userInfo';
  static URL_REST_INGREDIENTS = Constants.URL_RESOURCE_BASIS + '/ingredients';
  static URL_REST_TACOS = Constants.URL_RESOURCE_BASIS + '/tacos';
  static URL_REST_RECENT = Constants.URL_REST_TACOS + '/recent';

  // SPA
  static SPA_HOST = '192.168.1.103';
  static SPA_PORT = 4200;
  static URL_SPA_BASIS = 'http://' + Constants.SPA_HOST + ':' + Constants.SPA_PORT;
  static URL_REDIRECT = Constants.URL_SPA_BASIS;

  // oAuth2
  static URL_ACCESS_TOKEN = Constants.URL_REST_BASIS + '/oauth/token';
  static URL_CHECK_TOKEN = Constants.URL_REST_BASIS + '/oauth/check_token';
  static URL_REFRESH_TOKEN = Constants.URL_REST_BASIS + '/oauth/refresh_token';
  static THRESHOLD_REST_ERROR = 3;

  static JSON = 'application/json';
  static JSON_HEADER = new HttpHeaders({'Content-Type': Constants.JSON});
}
