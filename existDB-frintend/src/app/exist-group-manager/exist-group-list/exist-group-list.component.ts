import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {GroupsService} from '../service/groups.service';
import {DialogService} from '../../error-dialog/service/dialog.service';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {ExistGroupModel} from '../model/existGroup.model';
import {ExistAddGroupComponent} from '../exist-add-group/exist-add-group.component';
import {BehaviorSubject} from "rxjs";

@Component({
    selector: 'app-exist-group-list',
    templateUrl: './exist-group-list.component.html',
    styleUrls: ['./exist-group-list.component.sass']
})
export class ExistGroupListComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public addGroupData: ExistGroupModel;
    public GroupsData: any;
    public post: {
        groupName,
        groupManager,
        desc,
        groupMembers,
        default
    };
    public displayedColumns = ['groupName', 'groupManager', 'desc', 'details', 'delete'];

    constructor(public groupsServices: GroupsService,
                private router: Router,
                private dialog: MatDialog,
                private dialogService: DialogService,
                private groupService: GroupsService,
                private notificationService: NotificationService) {
    }

    // @ts-ignore
    @ViewChild(MatPaginator) paginator: MatPaginator;
    // @ts-ignore
    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.RenderGroupList();
    }

    RenderGroupList() {
        this.isLoading$.next(true);
        this.groupsServices.getExistGroups()
            .subscribe(
                res => {
                    this.GroupsData = new MatTableDataSource();
                    this.GroupsData.data = res;
                    this.GroupsData.sort = this.sort;
                    this.GroupsData.paginator = this.paginator;
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.warn('Error: ' + error);
                }
            );
    }

    details(groupName) {
        this.groupsServices.setSelectedGroup(groupName);
        this.router.navigateByUrl('/exist-group-edit-details');
    }

    addGroup() {
        const dialogRef = this.dialog.open(ExistAddGroupComponent, {
            width: '800px',
            height: '800px',
        });
        dialogRef.afterClosed().subscribe(result => {
            this.addGroupData = result;
            console.log(result);
            if (result === undefined) {
                this.notificationService.warn('Null Data');
            } else {
                this.groupService.addGroupToExist(this.addGroupData).subscribe(
                    data => {
                        this.notificationService.success('Success');
                    },
                    error => {
                        this.notificationService.warn('Error: ' + error);
                    }
                );
            }
        });
    }

    delete(groupName) {
        this.dialogService.openConfirmDialog('Are you sure to delete this record ?')
            .afterClosed().subscribe( res => {
                if (res) {
                    this.groupsServices.deleteGroup(groupName)
                        .subscribe(
                            data => {
                                this.notificationService.success('Success');
                                this.RenderGroupList();
                            },
                            error => {
                                this.notificationService.warn('Error: ' + error);
                            }
                        );
                }
        });

    }

}
