import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {Credentials} from '../../collection-manager/model/Credentials';
import {VersionManagementService} from '../service/version-management.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {ReversionsModel} from '../model/ReversionsModel';
import {GetDiffByRevModel} from '../model/GetDiffByRevModel';
import {ViewChangesDialogComponent} from '../view-changes-dialog/view-changes-dialog.component';
import {BehaviorSubject} from 'rxjs';
import {ErrorModel} from '../../error-notification-module/model/ErrorModel';

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

    // @ts-ignore
    @ViewChild(MatSort) sort: MatSort;
    // @ts-ignore
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
                this.notificationService.Error(error.error);
            }
        );
    }

    getChanges(rev) {
        const data: GetDiffByRevModel = {revNo: rev, path: this.selectedResCred.path + '/' + this.selectedResCred.name};
        this.versionManagementService.getDiffByRev(data).subscribe(
            result => {
                const dialogRef = this.dialog.open(ViewChangesDialogComponent, {
                    width: '60%',
                    height: '50%',
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
                this.notificationService.Error(error.error);
            }
        );
    }
}
