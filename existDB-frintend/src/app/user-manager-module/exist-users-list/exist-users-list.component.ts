import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-exist-users-list',
  templateUrl: './exist-users-list.component.html',
  styleUrls: ['./exist-users-list.component.sass']
})
export class ExistUsersListComponent implements OnInit {
  UsersData: any;
  post: {
    username,
    fullName,
    umask,
    primaryGroup,
    desc
  };
  displayedColumns = ['username', 'fullName', 'umask', 'primaryGroup', 'desc', 'details', 'delete'];
  constructor(public userService: UserService, private router: Router) { }

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.RenderUsersList();
  }

  RenderUsersList() {
    this.userService.getExistUsers()
        .subscribe(
            res => {
              this.UsersData = new MatTableDataSource();
              this.UsersData.data = res;
              this.UsersData.sort = this.sort;
              this.UsersData.paginator = this.paginator;
              console.log(this.UsersData.data);
            },
            error => {
              console.log('Error');
            }
        );
  }
  details(username) {
      this.UsersData.data.forEach((row) => {
          if (row.username === username) {
              this.userService.setSelectedExistUser(row);
              console.log('selectedUser: ' + row.username);
          }
      });
      this.router.navigateByUrl('/exist-user-edit-details');
  }

  delete(username) {
      console.log('user deleted: ' + username);
  }
}
