import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import {MatDialog, MatPaginator, MatSnackBar, MatSort, MatTableDataSource} from '@angular/material';
import {DialogService} from '../../error-dialog/service/dialog.service';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {ExistUserModel} from '../model/existUser.model';
import {ExistAddUserComponent} from '../exist-add-user/exist-add-user.component';
import {stringify} from "querystring";

@Component({
  selector: 'app-exist-users-list',
  templateUrl: './exist-users-list.component.html',
  styleUrls: ['./exist-users-list.component.sass']
})
export class ExistUsersListComponent implements OnInit {
    public addUserData: ExistUserModel;
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
                private dialog: MatDialog,
                private dialogService: DialogService,
                private notificationService: NotificationService) {
    }

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
                    this.notificationService.warn('Error: ' + error);
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
            .afterClosed().subscribe(res => {
            if (res) {
                this.userService.deleteUser(username).subscribe(
                    data => {
                        this.notificationService.success('Success');
                        this.RenderUsersList();
                    },
                    error => {
                        this.notificationService.warn('Error: ' + error);
                    }
                );
            }
        });
    }

    addUser() {
        const dialogRef = this.dialog.open(ExistAddUserComponent, {
            width: '800px',
            height: '800px',
        });
        dialogRef.afterClosed().subscribe(result => {
            this.addUserData = result;
            if (result !== undefined) {
                if (!this.addUserData.groups.includes(this.addUserData.primaryGroup)) {
                    this.addUserData.groups.push(this.addUserData.primaryGroup);
                }
                this.userService.addUserToExist(this.addUserData).subscribe(
                    data => {
                        this.notificationService.success('User created');
                        this.RenderUsersList();
                    },
                    error => {
                        this.notificationService.warn('Error: ' + stringify(error));
                    }
                );
            }
        });
    }
}
