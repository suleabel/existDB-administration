import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {AuthService} from '../auth-module/auth.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.sass']
})
export class FooterComponent implements OnInit {
  isLoggedIn$: Observable<boolean>;

  constructor(private authServices: AuthService) { }

  ngOnInit() {
    this.isLoggedIn$ = this.authServices.isLoggedIn;
  }

}
