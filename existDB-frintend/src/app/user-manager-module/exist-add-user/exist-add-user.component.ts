import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../service/user.service';
import {MatDialogRef, MatSnackBar} from '@angular/material';
import {NotificationService} from '../../error-dialog/service/notification.service';

@Component({
  selector: 'app-exist-add-user',
  templateUrl: './exist-add-user.component.html',
  styleUrls: ['./exist-add-user.component.sass']
})
export class ExistAddUserComponent implements OnInit {
  public addUsrForm: FormGroup;
  public existGroups: [];

  constructor(
      private formBuilder: FormBuilder,
      private userService: UserService,
      private snackBar: MatSnackBar,
      private notificationService: NotificationService,
      private dialogRef: MatDialogRef<ExistAddUserComponent>) {}

  ngOnInit() {
    this.getGroupNames();
    this.addUsrForm = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
      primaryGroup: [null, Validators.required],
      groups: [null, Validators.required],
      fullName: [],
      desc: []
    });
  }

  getGroupNames() {
    this.userService.getGroupsNames()
        .subscribe(
            data => {
              this.existGroups = data;
            },
            error => {
              this.notificationService.Error(error.error);
            }
        );
  }

  onNoClick(): void {
    this.dialogRef.close();
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
