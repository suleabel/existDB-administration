import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ExistUserModel} from '../model/existUser.model';
import {UserService} from '../service/user.service';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-exist-add-user',
  templateUrl: './exist-add-user.component.html',
  styleUrls: ['./exist-add-user.component.sass']
})
export class ExistAddUserComponent implements OnInit {
  public addUsrForm: FormGroup;
  public addUserData: ExistUserModel;
  public existGroups: [];

  constructor(
      private formBuilder: FormBuilder,
      private userService: UserService,
      private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.userService.getGroupsNames()
        .subscribe(
        data => {
          this.existGroups = data;
          console.log(this.existGroups);
        },
            error => {
          console.log('Error: ' + error);
        }
    );

    this.addUsrForm = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
      primaryGroup: [null, Validators.required],
      groups: [null, Validators.required],
      fullName: [],
      desc: []
    });
    console.log(this.addUsrForm);
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'back', {
      duration: 2000,
    });
  }

  submit() {
    this.addUserData = this.addUsrForm.value;
    if (!this.addUserData.groups.includes(this.addUserData.primaryGroup)) {
      this.addUserData.groups.push(this.addUserData.primaryGroup);
    }
    console.log(this.addUserData);
    this.userService.addUserToExist(this.addUserData).subscribe(
        data => {
          console.log('success: ' + data);
          this.openSnackBar(data);
          this.addUsrForm.reset();
        },
        error => {
          console.log('Error: ' + error.error.message);
        }
    );
    location.reload();
  }

  get username() {
    return this.addUsrForm.get('username');
  }

  get password() {
    return this.addUsrForm.get('password');
  }

  get primaryGroup() {
    return this.addUsrForm.get('primaryGroup');
  }

  get fullName() {
    return this.addUsrForm.get('fullName');
  }

  get desc() {
    return this.addUsrForm.get('desc');
  }

}
