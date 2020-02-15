import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import {UsersListModel} from '../model/users-list.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.sass']
})
export class UserListComponent implements OnInit {
  UsersData: any;
  post: {
    id,
    username,
    firstName,
    lastName,
    email
  };
  displayedColumns = ['id', 'username', 'firstName', 'lastName', 'email', 'details', 'delete'];

  constructor(public userService: UserService, private router: Router) {
  }

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.RenderUsersList();
  }

  RenderUsersList() {
    this.userService.getAllUser()
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

  details(id) {
    this.UsersData.data.forEach((row) => {
      if (row.id === id) {
        this.userService.setSelectedUser(row);
      }
    });
    // this.userService.setSelectedUser(id);
    this.router.navigateByUrl('/user-detail-list');
  }

  delete(id) {
    console.log(id);
  }
}
