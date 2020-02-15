import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {UsersListModel} from '../model/users-list.model';
import {UserRole} from '../model/role.model';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.sass']
})
export class UserDetailsComponent implements OnInit {
  userDetailsEdit = false;
  selectedUser: UsersListModel = null;
  selectedRole: string;

  userRoles: Array<UserRole> = [
    {id: 'user', name: 'User'},
    {id: 'admin', name: 'Administrator'},
    {id: 'pm', name: 'Project Manager'}
  ];

  constructor(
    private userService: UserService,
    private router: Router) {
  }

  ngOnInit() {
    this.selectedUser = this.userService.getSelectedUser();
    if (this.selectedUser === null) {
      this.router.navigateByUrl('/user-manager-module');
    }
  }

  save() {
    if (this.selectedRole === 'admin') {
      this.selectedUser.roles.push('admin');
      this.selectedUser.roles.push('pm');
    } else if (this.selectedRole === 'pm') {
      this.selectedUser.roles.push('pm');
    }
    this.userService.saveUser(this.selectedUser).subscribe(
      data => {
        console.log('Success: ' + JSON.stringify(data));
      }, error => {
        console.log(error.error.text);
      }
    );
  }
}
