import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort} from '@angular/material';
import {TokenStorageService} from '../../auth-module/token-storage.service';
import {XmlToXsdService} from '../../xml-to-xsd/service/xml-to-xsd.service';

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

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(private token: TokenStorageService) {
  }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername()
    };
    console.log(this.info);
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }

}




