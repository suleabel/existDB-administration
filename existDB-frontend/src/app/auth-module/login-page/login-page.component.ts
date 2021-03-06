import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {TokenStorageService} from '../token-storage.service';
import {BehaviorSubject} from 'rxjs';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.sass']
})
export class LoginPageComponent implements OnInit {
  public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public loginForm: FormGroup;
  public errorMessage: string;

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private formBuilder: FormBuilder,
    private router: Router) {
  }

  ngOnInit() {
    this.authService.setLogout();
    this.loginForm = this.formBuilder.group({
      username: ['', [
          Validators.required
      ]],
      password: ['', [
          // Validators.required
      ]],
      url: ['YOLOZSEFA.ASUSCOMM.COM:8081', [
          Validators.required
      ]]
    });
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  get url() {
    return this.loginForm.get('url');
  }

  submit() {
    if (this.loginForm.invalid) {
      return;
    }
    this.loggedIn();
  }

  loggedIn() {
    this.isLoading$.next(true);
    this.authService.attemptAuth(this.loginForm.value).subscribe(
      data => {
        TokenStorageService.saveToken(data.accessToken);
        TokenStorageService.saveUsername(data.username);
        TokenStorageService.saveAuthorities(data.authorities);
        this.isLoading$.next(false);
        this.authService.setDBVersion();
        this.authService.setServerIp();
        this.router.navigate(['home']);
      },
      error => {
        this.errorMessage = error.error.message;
        console.log('Login errorMessage  ' + this.errorMessage);
        this.isLoading$.next(false);
      }
    );
  }
  //
  // getDBVersion() {
  //   this.authService.getDbVersion().subscribe(data => {
  //       },
  //       error => {
  //         // console.log(error.error);
  //       });
  // }
  //
  // getServerIp() {
  //   this.authService.getServerIp().subscribe(data => {
  //       },
  //       error => {
  //         // console.log(error.error);
  //       });
  // }
}
