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
    this.loginForm = this.formBuilder.group({
      username: ['', [
          Validators.required
      ]],
      password: ['', [
          // Validators.required
      ]],
      url: ['YOLOZSEFA.ASUSCOMM.COM:8089', [
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
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        this.isLoading$.next(false);
        this.router.navigate(['home']);
      },
      error => {
        this.errorMessage = error.error.message;
        console.log('Login errorMessage  ' + this.errorMessage);
        // this.loginForm.reset();
        this.isLoading$.next(false);
      }
    );
  }
}
