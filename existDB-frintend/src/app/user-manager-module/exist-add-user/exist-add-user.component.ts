import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ExistUserModel} from '../model/existUser.model';
import {UserService} from '../service/user.service';
import {MatSnackBar} from '@angular/material';
import {NotificationService} from '../../error-dialog/service/notification.service';

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
      private snackBar: MatSnackBar,
      private notificationService: NotificationService) {}

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
  }

  submit() {
    this.addUserData = this.addUsrForm.value;
    if (!this.addUserData.groups.includes(this.addUserData.primaryGroup)) {
      this.addUserData.groups.push(this.addUserData.primaryGroup);
    }
    console.log(this.addUserData);
    this.userService.addUserToExist(this.addUserData).subscribe(
        data => {
          this.notificationService.success('User created');
          this.addUsrForm.reset();
        },
        error => {
          this.notificationService.warn('Error: ' + error);
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
