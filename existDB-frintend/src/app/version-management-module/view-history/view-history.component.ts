import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Credentials} from '../../collection-manager/model/Credentials';
import {VersionManagementService} from '../service/version-management.service';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {VersionsModel} from '../model/VersionsModel';
import {ReversionsModel} from '../model/ReversionsModel';
import {GetDiffByRevModel} from '../model/GetDiffByRevModel';
import {ViewChangesDialogComponent} from '../view-changes-dialog/view-changes-dialog.component';

@Component({
    selector: 'app-view-history',
    templateUrl: './view-history.component.html',
    styleUrls: ['./view-history.component.sass']
})
export class ViewHistoryComponent implements OnInit {
    public ok: boolean;
    public selectedResHistory: VersionsModel;
    public resReversions: ReversionsModel;
    public selectedResCred: Credentials;
    public displayedColumns: string[] = ['rev', 'user', 'date', 'changes', 'restore'];

    constructor(public dialogRef: MatDialogRef<ViewHistoryComponent>,
                @Inject(MAT_DIALOG_DATA) public data,
                public versionManagementService: VersionManagementService,
                public notificationService: NotificationService,
                private dialog: MatDialog) {
    }

    ngOnInit() {
        this.ok = true;
        this.selectedResCred = this.data.res;
        console.log(this.selectedResCred);
        this.getHistroy(this.selectedResCred);
    }

    getHistroy(element: Credentials) {
        this.versionManagementService.getResHistory(element.path + '/' + element.name).subscribe(
            data => {
                this.selectedResHistory = data;
                this.resReversions = this.selectedResHistory.reversions;
                console.log(this.resReversions);
            },
            error => {
                console.log(error);
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
                this.notificationService.Error(error.error);
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
                console.log('new content: ' + result);
                this.notificationService.success('Success');
            },
            error => {
                this.notificationService.Error(error.error);
            }
        );
    }
}
