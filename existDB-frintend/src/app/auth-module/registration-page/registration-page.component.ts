import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {SignUpInfo} from '../signup-info';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.sass']
})
export class RegistrationPageComponent implements OnInit {
  public regForm: FormGroup;
  public errorMessage: string;
  public passwordPattern: '^[0-9]';
  public signUpInfo: SignUpInfo;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService) {
  }

  ngOnInit() {
    this.regForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.pattern(this.passwordPattern)]],
      passwordConfirm: ['', [Validators.required]],
    });
  }

  get username() {
    return this.regForm.get('username');
  }

  get firstName() {
    return this.regForm.get('firstName');
  }

  get lastName() {
    return this.regForm.get('lastName');
  }

  get email() {
    return this.regForm.get('email');
  }

  get password() {
    return this.regForm.get('password');
  }

  get passwordConfirm() {
    return this.regForm.get('passwordConfirm');
  }

  submit() {
    this.signUpInfo = this.regForm.value;
    this.signUpInfo.role = ['user'];
    console.log(this.signUpInfo);
    if (this.regForm.invalid) {
      return;
    }
    this.signUp();
  }

  signUp() {
    this.authService.signUp(this.regForm.value).subscribe(
      data => {
        this.router.navigateByUrl('/login');
      },
      error => {
        this.errorMessage = error.error.message;
        console.log(this.errorMessage);
      }
    );
  }
}
