import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../../file-explorer/service/file-explorer.service';
import {Credentials} from '../../file-explorer/model/Credentials';
import {EditTriggerModel} from '../model/EditTriggerModel';
import {DialogService} from '../../error-dialog/service/dialog.service';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {AddTriggerComponent} from '../add-trigger/add-trigger.component';

@Component({
    selector: 'app-xml-file-viewer',
    templateUrl: './xml-file-viewer.component.html',
    styleUrls: ['./xml-file-viewer.component.sass']
})
export class XmlFileViewerComponent implements OnInit {
    private viewResult: string;
    private openedFile: Credentials;

    constructor(public dialogRef: MatDialogRef<XmlFileViewerComponent>,
                private fileExplorerService: FileExplorerService,
                private dialogService: DialogService,
                private notificationService: NotificationService,
                private dialog: MatDialog) {
    }

    ngOnInit() {
        this.openedFile = this.fileExplorerService.openedFile;
        this.fileExplorerService.getXmlResContent(this.openedFile.path + '/' + this.openedFile.name)
            .subscribe(
                data => {
                    this.viewResult = data;
                },
                error => {
                    console.log(error);
                }
            );
    }

    onAddTrigger() {
        const dialogDirNameConfig = new MatDialogConfig();
        dialogDirNameConfig.disableClose = true;
        dialogDirNameConfig.autoFocus = true;
        dialogDirNameConfig.width = '60%';
        this.dialog.open(AddTriggerComponent, dialogDirNameConfig);
    }

    onClose() {
        this.fileExplorerService.setSaveContentHere('');
        this.dialogRef.close();
    }

}
