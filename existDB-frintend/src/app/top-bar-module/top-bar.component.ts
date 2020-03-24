import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth-module/auth.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.sass']
})
export class TopBarComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit() { }

  onLogout() {
    this.authService.logout();
  }
}
