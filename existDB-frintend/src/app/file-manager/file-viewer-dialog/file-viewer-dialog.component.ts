import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FileManagerService} from '../service/file-manager.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';

@Component({
    selector: 'app-file-viewer-dialog',
    templateUrl: './file-viewer-dialog.component.html',
    styleUrls: ['./file-viewer-dialog.component.sass']
})
export class FileViewerDialogComponent implements OnInit {
    public fullPath: string;
    public content: string;
    public isEdit = false;
    public editedContent: string;

    constructor(private dialogRef: MatDialogRef<FileViewerDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data,
                private fileManagerService: FileManagerService,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.fullPath = this.data.url + '/' + this.data.fileData.name;
        this.readFile(this.fullPath);
    }

    readFile(fullPath) {
        this.fileManagerService.readFile(fullPath)
            .subscribe(data => {
                    this.content = data.response;
                }, error => {
                    this.notificationService.Error(error.error);
                }
            );
    }

    onEdit() {
        this.editedContent = this.content;
        this.isEdit = true;
    }

    onSave() {
        const fileCred = {
            content: this.editedContent,
            path: this.data.url,
            name: this.data.fileData.name,
            parameter: '',
            isXml: this.data.fileData.name.includes('xml'),
        };
        this.fileManagerService.editFile(fileCred).subscribe(
            result => {
                this.notificationService.success('Saved');
                this.isEdit = false;
                this.readFile(this.fullPath);
            },
            error => {
                console.log(error);
                this.notificationService.Error(error.error);
            }
        );
    }

    onClose() {
        this.dialogRef.close();
    }

}
