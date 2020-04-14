import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../../auth-module/token-storage.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.sass']
})
export class HomePageComponent implements OnInit {

  info: {
    token: string,
    username: string
  };

  constructor(private token: TokenStorageService) {
  }

  ngOnInit() {
    this.info = {
      token: TokenStorageService.getToken(),
      username: TokenStorageService.getUsername()
    };
  }
}




