import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {Credentials} from '../../collection-manager/model/Credentials';
import {VersionManagementService} from '../service/version-management.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {ReversionsModel} from '../model/ReversionsModel';
import {GetDiffByRevModel} from '../model/GetDiffByRevModel';
import {ViewChangesDialogComponent} from '../view-changes-dialog/view-changes-dialog.component';
import {BehaviorSubject} from 'rxjs';
import {ErrorModel} from '../../error-notification-module/model/ErrorModel';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';

@Component({
    selector: 'app-view-history',
    templateUrl: './view-history.component.html',
    styleUrls: ['./view-history.component.sass']
})
export class ViewHistoryComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public ok: boolean;
    public TableData: any;
    public element: ReversionsModel;
    public selectedResCred: Credentials;
    public displayedColumns: string[] = ['rev', 'user', 'date', 'changes', 'restore'];

    constructor(public dialogRef: MatDialogRef<ViewHistoryComponent>,
                @Inject(MAT_DIALOG_DATA) public data,
                public versionManagementService: VersionManagementService,
                public notificationService: NotificationService,
                private dialog: MatDialog) {
    }

    @ViewChild(MatSort) sort: MatSort;
    @ViewChild(MatPaginator) paginator: MatPaginator;

    ngOnInit() {
        this.ok = true;
        this.selectedResCred = this.data.res;
        this.getHistory(this.selectedResCred);
    }

    getHistory(res) {
        this.isLoading$.next(true);
        this.versionManagementService.getResHistory(res.path + '/' + res.name).subscribe(
            data => {
                this.TableData = new MatTableDataSource(data.reversions);
                this.TableData.sort = this.sort;
                this.TableData.paginator = this.paginator;
                this.isLoading$.next(false);
            },
            error => {
                if (error.error.message.toLowerCase().includes('SOURCE NOT FOUND'.toLowerCase())) {
                   this.notificationService.Error2('Version management module is not installed, please install it first.');
                } else {
                    this.notificationService.Error(error.error);
                }
            }
        );
    }

    getChanges(rev) {
        const data: GetDiffByRevModel = {revNo: rev, path: this.selectedResCred.path + '/' + this.selectedResCred.name};
        this.versionManagementService.getDiffByRev(data).subscribe(
            result => {
                const dialogRef = this.dialog.open(ViewChangesDialogComponent, {
                    width: '60%',
                    height: 'auto',
                    maxHeight: '80%',
                    data: {res: result}
                });
                dialogRef.afterClosed().subscribe();
            },
            error => {
                const jsonError: ErrorModel = JSON.parse(error.error);
                this.notificationService.Error(jsonError);
            }
    );
    }

    onClose() {
        this.dialogRef.close();
    }

    restore(rev) {
        const data: GetDiffByRevModel = {revNo: rev, path: this.selectedResCred.path + '/' + this.selectedResCred.name};
        this.versionManagementService.restoreResByRev(data).subscribe(
            result => {
                this.notificationService.success('Success');
            },
            error => {
                const jsonError: ErrorModel = JSON.parse(error.error);
                this.notificationService.Error(jsonError);
            }
        );
    }
}
