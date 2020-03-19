import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../service/file-explorer.service';
import {Credentials} from '../model/Credentials';
import {StoreResourceModel} from '../model/StoreResourceModel';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
    selector: 'app-resource-viewer-dialog',
    templateUrl: './resource-viewer-dialog.component.html',
    styleUrls: ['./resource-viewer-dialog.component.sass']
})
export class ResourceViewerDialogComponent implements OnInit {
    public originalContent: string;
    public isBinary: boolean;
    public openedFile: Credentials;
    public isEdit = false;
    public editedContent = '';
    public fileUrl;

    constructor(public dialogRef: MatDialogRef<ResourceViewerDialogComponent>,
                private fileExplorerService: FileExplorerService,
                private notificationService: NotificationService,
                private sanitizer: DomSanitizer) {
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

    onDownload() {
        const blob = new Blob([this.originalContent], {type: 'application/xml'});
        this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
    }

    onSave() {
        const saveRes: StoreResourceModel = {url: this.openedFile.path, fileName: this.openedFile.name, content: this.editedContent, isBinary: this.isBinary, mime: this.openedFile.mime};
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
        this.dialogRef.close();
    }

}
