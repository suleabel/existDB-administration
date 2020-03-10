import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {GroupsService} from '../service/groups.service';
import {DialogService} from '../../error-dialog/service/dialog.service';
import {NotificationService} from '../../error-dialog/service/notification.service';

@Component({
    selector: 'app-exist-group-list',
    templateUrl: './exist-group-list.component.html',
    styleUrls: ['./exist-group-list.component.sass']
})
export class ExistGroupListComponent implements OnInit {
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
                private dialogService: DialogService,
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
        this.groupsServices.getExistGroups()
            .subscribe(
                res => {
                    this.GroupsData = new MatTableDataSource();
                    this.GroupsData.data = res;
                    this.GroupsData.sort = this.sort;
                    this.GroupsData.paginator = this.paginator;
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

    delete(groupName) {
        this.dialogService.openConfirmDialog('Are you sure to delete this record ?')
            .afterClosed().subscribe( res => {
                if (res) {
                    this.groupsServices.deleteGroup(groupName)
                        .subscribe(
                            data => {
                                this.notificationService.success('Success');
                                location.reload();
                            },
                            error => {
                                this.notificationService.warn('Error: ' + error);
                            }
                        );
                }
        });

    }

}
