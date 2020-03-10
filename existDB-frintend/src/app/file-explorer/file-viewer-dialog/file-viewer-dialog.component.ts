import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../service/file-explorer.service';
import {Credentials} from '../model/Credentials';
import {StoreResourceModel} from '../model/StoreResourceModel';
import {NotificationService} from '../../error-dialog/service/notification.service';

@Component({
    selector: 'app-file-viewer-dialog',
    templateUrl: './file-viewer-dialog.component.html',
    styleUrls: ['./file-viewer-dialog.component.sass']
})
export class FileViewerDialogComponent implements OnInit {
    public originalContent: string;
    public isBinary: boolean;
    public openedFile: Credentials;
    public isEdit = false;
    public editedContent = '';

    constructor(public dialogRef: MatDialogRef<FileViewerDialogComponent>,
                private fileExplorerService: FileExplorerService,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.openedFile = this.fileExplorerService.openedFile;
        this.fileExplorerService.getResContent(this.fileExplorerService.openedFile.path + '/' + this.fileExplorerService.openedFile.name)
            .subscribe(
                data => {
                    this.originalContent = data.content;
                    this.isBinary = data.isBinary;
                },
                error => {
                    this.notificationService.warn('Error: ' + error);
                    console.log(error);
                }
            );
    }

    onEdit() {
        this.editedContent = this.originalContent;
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

    onClose() {
        this.fileExplorerService.setSaveContentHere('');
        this.dialogRef.close();
    }

}
