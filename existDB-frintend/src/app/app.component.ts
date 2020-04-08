import {Component, OnInit} from '@angular/core';
import {AuthService} from './auth-module/auth.service';
import {BehaviorSubject, Observable} from 'rxjs';
// @ts-ignore
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {
  isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  constructor(private authService: AuthService) {}
  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn;
  }
}
