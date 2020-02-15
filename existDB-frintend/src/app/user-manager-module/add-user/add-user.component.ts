import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../auth-module/auth.service';
import {SignUpInfo} from '../../auth-module/signup-info';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.sass']
})
export class AddUserComponent implements OnInit {
  public addUsrForm: FormGroup;
  public errorMessage = '';
  public addUserInfo: SignUpInfo;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.addUsrForm = this.formBuilder.group({
      username: [null, Validators.required],
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      email: [null, Validators.required, Validators.email],
      password: [null, Validators.required],
      roleIsAdmin: [],
      roleIsPm: []
    });

    console.log(this.addUsrForm);
  }

  get username() {
    return this.addUsrForm.get('username');
  }

  get firstName() {
    return this.addUsrForm.get('firstName');
  }

  get lastName() {
    return this.addUsrForm.get('lastName');
  }

  get email() {
    return this.addUsrForm.get('email');
  }

  get password() {
    return this.addUsrForm.get('password');
  }

  submit() {
    this.addUserInfo = this.addUsrForm.value;
    this.addUserInfo.role = ['user'];

    if (this.addUsrForm.invalid) {
      return;
    }
    if (this.addUsrForm.controls.roleIsAdmin.value) {
      this.addUserInfo.role.push('admin');
    }
    if (this.addUsrForm.controls.roleIsPm.value) {
      this.addUserInfo.role.push('pm');
    }

    console.log(this.addUserInfo);

    this.addUser();
  }

  addUser() {
    this.authService.signUp(this.addUserInfo).subscribe(
      data => {
        console.log('success');
        this.addUsrForm.reset();
      },
      error => {
        this.errorMessage = error.error.message;
        console.log(this.errorMessage);
      }
    );
  }
}
