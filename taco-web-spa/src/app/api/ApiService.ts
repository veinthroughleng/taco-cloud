import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import {Constants} from "../utils/Constants";

@Injectable()
export class ApiService {

  constructor(private http: Http) {
  }

  get(path: String) {
    return this.http.get(Constants.REST_URL_BASIS + path);
  }

}
