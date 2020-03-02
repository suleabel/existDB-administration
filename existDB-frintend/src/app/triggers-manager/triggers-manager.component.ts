import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {DialogService} from './service/dialog.service';
import {CollectionsDialogComponent} from './collections-dialog/collections-dialog.component';
import {TriggersService} from './service/triggers.service';

@Component({
    selector: 'app-triggers-manager',
    templateUrl: './triggers-manager.component.html',
    styleUrls: ['./triggers-manager.component.sass']
})
export class TriggersManagerComponent implements OnInit {

    constructor(
        private dialogService: DialogService,
        private dialog: MatDialog,
        private triggerService: TriggersService) {
    }

    ngOnInit() {
    }

    openDialog() {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.width = '60%';
        this.dialog.open(CollectionsDialogComponent, dialogConfig);
    }

    getSelectedCollection() {
        console.log(this.triggerService.selectedCollection);
    }

}
