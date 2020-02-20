import { Component, OnInit } from '@angular/core';
import {ExistUserModel} from '../model/existUser.model';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-exist-users-edit-details',
  templateUrl: './exist-users-edit-details.component.html',
  styleUrls: ['./exist-users-edit-details.component.sass']
})
export class ExistUsersEditDetailsComponent implements OnInit {
  userDetailsEdit = false;
  selectedUser: ExistUserModel = null;
  userGroups: Array<Map<number, string>> = null;

  constructor(
      private userService: UserService,
      private router: Router) { }

  ngOnInit() {
    this.selectedUser = this.userService.getSelectedExistUser();
    if (this.selectedUser === null) {
      this.router.navigateByUrl('/user-manager-module');
    }
  }

  save() {
    console.log('dummy save');
  }

}
