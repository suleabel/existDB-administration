import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from './auth-module/token-storage.service';
import {AuthService} from './auth-module/auth.service';

// @ts-ignore
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {
  // private roles: string[];
  // private authority: string;

  constructor(private tokenStorage: TokenStorageService, private authService: AuthService) {
  }

  ngOnInit() {
    // if (this.tokenStorage.getToken()) {
    //   this.roles = this.tokenStorage.getAuthorities();
    //   this.roles.every(role => {
    //     if (role === 'ROLE_ADMIN') {
    //       this.authority = 'admin';
    //       return false;
    //     } else if (role === 'ROLE_RM') {
    //       this.authority = 'pm';
    //       return false;
    //     }
    //     this.authority = 'user';
    //     return true;
    //   });
    // }
  }
}
