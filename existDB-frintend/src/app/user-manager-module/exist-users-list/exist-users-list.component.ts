import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import {MatPaginator, MatSnackBar, MatSort, MatTableDataSource} from '@angular/material';
import {DialogService} from '../../error-dialog/service/dialog.service';
import {NotificationService} from '../../error-dialog/service/notification.service';

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
    desc,
    default
  };
  displayedColumns = ['username', 'fullName', 'umask', 'primaryGroup', 'desc', 'default', 'details', 'delete'];
  constructor(private userService: UserService,
              private router: Router,
              private snackBar: MatSnackBar,
              private dialogService: DialogService,
              private notificationService: NotificationService) { }

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
              console.log('Error:' + error);
            }
        );
  }
  details(username) {
      this.UsersData.data.forEach((row) => {
          if (row.username === username) {
              this.userService.setSelectedExistUser(row);
          }
      });
      this.router.navigateByUrl('/exist-user-edit-details');
  }

  delete(username) {
      this.dialogService.openConfirmDialog('Are you sure to delete this record ?')
          .afterClosed().subscribe( res => {
              if (res) {
                  this.userService.deleteUser(username).subscribe(
                      data => {
                          console.log('Response: ' + res);
                          this.notificationService.success('Success');
                          location.reload();
                      },
                      error => {
                          console.log('Error: ' + error);
                          this.notificationService.warn('Error: ' + error);
                      }
                  );
              }
      });
  }
}
