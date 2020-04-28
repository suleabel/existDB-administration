import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import {DialogService} from '../../error-notification-module/service/dialog.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {ExistUserModel} from '../model/existUser.model';
import {ExistAddUserComponent} from '../exist-add-user/exist-add-user.component';
import {BehaviorSubject} from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';

@Component({
    selector: 'app-exist-users-list',
    templateUrl: './exist-users-list.component.html',
    styleUrls: ['./exist-users-list.component.sass']
})
export class ExistUsersListComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public addUserData: ExistUserModel;
    public UsersData: any;
    public post: {
        username,
        fullName,
        umask,
        primaryGroup,
        desc,
        default
    };
    public displayedColumns = ['username', 'fullName', 'umask', 'primaryGroup', 'desc', 'default', 'details', 'delete'];

    constructor(private userService: UserService,
                private router: Router,
                private dialog: MatDialog,
                private dialogService: DialogService,
                private notificationService: NotificationService) {
    }
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.RenderUsersList();
    }

    RenderUsersList() {
        this.isLoading$.next(true);
        this.userService.getExistUsers()
            .subscribe(
                res => {
                    this.UsersData = new MatTableDataSource(res);
                    this.UsersData.sort = this.sort;
                    this.UsersData.paginator = this.paginator;
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.Error(error.error);
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
                        this.notificationService.Error(error.error);
                    }
                );
            }
        });
    }

    addUser() {
        const dialogRef = this.dialog.open(ExistAddUserComponent, {
            width: '60%',
            height: '90%',
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
                        this.notificationService.Error(error.error);
                    }
                );
            }
        });
    }
}
