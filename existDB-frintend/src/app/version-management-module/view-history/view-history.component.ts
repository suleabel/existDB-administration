import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Credentials} from '../../collection-manager/model/Credentials';
import {VersionManagementService} from '../service/version-management.service';

@Component({
    selector: 'app-view-history',
    templateUrl: './view-history.component.html',
    styleUrls: ['./view-history.component.sass']
})
export class ViewHistoryComponent implements OnInit {
    public ok: boolean;
    public selectedResHistory;
    public selectedResCred: Credentials;

    constructor(public dialogRef: MatDialogRef<ViewHistoryComponent>,
                @Inject(MAT_DIALOG_DATA) public data,
                public versionManagementService: VersionManagementService) {
    }

    ngOnInit() {
        this.ok = true;
        this.selectedResCred = this.data.res;
        console.log(this.selectedResCred);
    }

    getHistroy() {

    }

    onClose() {
        this.dialogRef.close();
    }

}
