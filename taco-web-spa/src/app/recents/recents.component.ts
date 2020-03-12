import {Component, OnInit, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Constants} from "../utils/Constants";

@Component({
  selector: 'recent-tacos',
  templateUrl: 'recents.component.html',
  styleUrls: ['./recents.component.css']
})

@Injectable()
export class RecentTacosComponent implements OnInit {
  recentTacos: any;

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
    this.httpClient.get(Constants.URL_REST_RECENT) // <1>
      .subscribe(
        // prototype version
        (data: any) => this.recentTacos = data

        // href version
        // (data: any) => this.recentTacos = data._embedded.tacos
      );
  }
}
