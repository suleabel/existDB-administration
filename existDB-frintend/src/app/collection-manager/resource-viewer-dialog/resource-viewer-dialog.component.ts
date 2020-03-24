import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../service/file-explorer.service';
import {Credentials} from '../model/Credentials';
import {StoreResourceModel} from '../model/StoreResourceModel';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {DomSanitizer} from '@angular/platform-browser';
import {EvalResultViewerComponent} from '../eval-result-viewer/eval-result-viewer.component';

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
                private sanitizer: DomSanitizer,
                private dialog: MatDialog) {
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
                    this.notificationService.warn(error.error.message);
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
        const saveRes: StoreResourceModel = {
            url: this.openedFile.path,
            fileName: this.openedFile.name,
            content: this.editedContent,
            isBinary: this.isBinary,
            mime: this.openedFile.mime
        };
        console.log(saveRes);
        this.fileExplorerService.editResource(saveRes)
            .subscribe(data => {
                    this.notificationService.success('Saved: ' + data);
                },
                error => {
                    this.notificationService.warn(error.error.message);
                });
        this.isEdit = false;
        this.dialogRef.close();
    }

    eval() {
        this.fileExplorerService.evalXqueryFromPath(this.openedFile.path + '/' + this.openedFile.name)
            .subscribe(result => {
                this.notificationService.success('Success');
                const dialogRef = this.dialog.open(EvalResultViewerComponent, {
                        width: '80%',
                        height: 'auto',
                        data: {res: result}
                    });
                dialogRef.afterClosed().subscribe();
            },
            error => {
                this.notificationService.warn(error.error.message);
            });
    }

    onClose() {
        this.dialogRef.close();
    }

}
