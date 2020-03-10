import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../../file-explorer/service/file-explorer.service';
import {Credentials} from '../../file-explorer/model/Credentials';
import {DialogService} from '../../error-dialog/service/dialog.service';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {AddTriggerComponent} from '../add-trigger/add-trigger.component';
import {StoreResourceModel} from '../../file-explorer/model/StoreResourceModel';

@Component({
    selector: 'app-xml-file-viewer',
    templateUrl: './xml-file-viewer.component.html',
    styleUrls: ['./xml-file-viewer.component.sass']
})
export class XmlFileViewerComponent implements OnInit {
    public viewResult: string;
    public isBinary: boolean;
    public openedFile: Credentials;
    public isEdit = false;
    public editedContent = '';

    constructor(public dialogRef: MatDialogRef<XmlFileViewerComponent>,
                private fileExplorerService: FileExplorerService,
                private dialogService: DialogService,
                private notificationService: NotificationService,
                private dialog: MatDialog) {
    }

    ngOnInit() {
        this.openedFile = this.fileExplorerService.openedFile;
        this.fileExplorerService.getResContent(this.openedFile.path + '/' + this.openedFile.name)
            .subscribe(
                data => {
                    console.log(data);
                    this.viewResult = data.content;
                    this.isBinary = data.isBinary;

                },
                error => {
                    this.notificationService.warn('Error: ' + error);
                }
            );
    }

    onEdit() {
        this.editedContent = this.viewResult;
        this.isEdit = true;
    }

    onSave() {
        const saveRes: StoreResourceModel = {url: this.openedFile.path, fileName: this.openedFile.name, content: this.editedContent, isBinary: this.isBinary};
        console.log(saveRes);
        this.fileExplorerService.editResource(saveRes)
            .subscribe(data => {
                    this.notificationService.success('Saved: ' + data);
                },
                error => {
                    this.notificationService.warn('Error: ' + error);
                });
        this.isEdit = false;
        this.dialogRef.close();
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
