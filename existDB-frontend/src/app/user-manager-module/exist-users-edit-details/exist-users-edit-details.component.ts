import { Component, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ExistUserModel} from '../model/existUser.model';
import {NotificationService} from '../../error-notification-module/service/notification.service';

@Component({
  selector: 'app-exist-users-edit-details',
  templateUrl: './exist-users-edit-details.component.html',
  styleUrls: ['./exist-users-edit-details.component.sass']
})
export class ExistUsersEditDetailsComponent implements OnInit {
  public editUsrForm: FormGroup;
  public userDetailsEdit = false;
  public selectedUser: ExistUserModel = null;
  public existGroups: string[];
  public editUserData: ExistUserModel;


  constructor(
      private userService: UserService,
      private formBuilder: FormBuilder,
      private notificationService: NotificationService,
      private router: Router) { }

  ngOnInit() {
    this.selectedUser = this.userService.getSelectedExistUser();
    if (this.selectedUser === null) {
      this.router.navigateByUrl('/user-manager-module');
    }
    this.getAllExistGroup();
    this.editUsrForm = this.formBuilder.group({
      username: [this.selectedUser.username, Validators.required],
      primaryGroup: [this.selectedUser.primaryGroup, Validators.required],
      umask: [this.selectedUser.umask, Validators.required],
      groups: [this.selectedUser.groups, Validators.required],
      password: [this.selectedUser.password],
      fullName: [this.selectedUser.fullName],
      desc: [this.selectedUser.desc]
    });
  }

  save() {
    this.editUserData = this.editUsrForm.value;
    if (!this.editUserData.groups.includes(this.editUserData.primaryGroup)) {
      this.editUserData.groups.push(this.editUserData.primaryGroup);
    }
    this.userService.editUser(this.editUserData)
        .subscribe( data => {
          this.notificationService.success('Saved');
          this.router.navigateByUrl('/user-manager-module');
        },
        error => {
          this.notificationService.Error(error.error);
        });
  }

  getAllExistGroup() {
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

  get username() {
    return this.editUsrForm.get('username');
  }


  get primaryGroup() {
    return this.editUsrForm.get('primaryGroup');
  }

  get fullName() {
    return this.editUsrForm.get('fullName');
  }

  get desc() {
    return this.editUsrForm.get('desc');
  }

  get umask() {
     return this.editUsrForm.get('umask');
  }

  get password() {
    return this.editUsrForm.get('password');
  }

}
