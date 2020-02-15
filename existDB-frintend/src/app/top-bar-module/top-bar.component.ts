import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth-module/auth.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.sass']
})
export class TopBarComponent implements OnInit {
  isLoggedIn$: Observable<boolean>;
  isAdmin$: Observable<boolean>;
  isPm$: Observable<boolean>;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn;
    this.isAdmin$ = this.authService.isAdmin;
    this.isPm$ = this.authService.isPm;
  }

  onLogout() {
    this.authService.logout();
  }
}
