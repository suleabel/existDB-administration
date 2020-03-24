import {Component, OnInit} from '@angular/core';
import {AuthService} from './auth-module/auth.service';
import {Observable} from 'rxjs';
// @ts-ignore
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {
  isLoggedIn$: Observable<boolean>;
  constructor(private authService: AuthService) {}
  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn;
  }
}
