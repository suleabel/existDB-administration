import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../../auth-module/token-storage.service';
import {TestService} from '../test.service';

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

  constructor(private token: TokenStorageService,
              private testService: TestService) {
  }

  ngOnInit() {
    // this.testService.test().subscribe( data => {
    //   console.log(data);
    // },
    //     error => {
    //   console.log('Error: ' + error);
    //     });
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername()
    };
    console.log(this.info);
  }
}




