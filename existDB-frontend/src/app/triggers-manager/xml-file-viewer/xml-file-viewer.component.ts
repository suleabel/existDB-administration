import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {DialogService} from '../../error-notification-module/service/dialog.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {AddTriggerComponent} from '../add-trigger/add-trigger.component';
import {TriggersService} from '../service/triggers.service';
import {FileExplorerService} from '../../collection-manager/service/file-explorer.service';
import {StoreResourceModel} from '../../collection-manager/model/StoreResourceModel';
import {BehaviorSubject} from 'rxjs';
import {XmlParserService} from '../../collection-manager/service/xml-parser.service';
import {Credentials} from '../../collection-manager/model/Credentials';

@Component({
    selector: 'app-xml-file-viewer',
    templateUrl: './xml-file-viewer.component.html',
    styleUrls: ['./xml-file-viewer.component.sass']
})
export class XmlFileViewerComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public viewResult: string;
    public isBinary: boolean;
    public isEdit = false;
    public editedContent = '';
    public fullPath: string;

    constructor(public dialogRef: MatDialogRef<XmlFileViewerComponent>,
                @Inject(MAT_DIALOG_DATA) public data,
                private fileExplorerService: FileExplorerService,
                private triggerService: TriggersService,
                private dialogService: DialogService,
                private notificationService: NotificationService,
                private dialog: MatDialog) {
    }

    ngOnInit() {
        this.fullPath = this.data.fileData.path + '/' + this.data.fileData.name;
        this.readContent(this.fullPath);
    }
    readContent(path) {
        this.fileExplorerService.getResContent(path)
            .subscribe(
                data => {
                    console.log(data);
                    this.viewResult = data.content;
                    this.isBinary = data.isBinary;

                },
                error => {
                    this.notificationService.Error(error.error);
                }
            );
    }

    onEdit() {
        this.editedContent = this.viewResult;
        this.isEdit = true;
    }

    onSave() {
        const saveRes: StoreResourceModel = {
            url: this.data.fileData.path,
            fileName: this.data.fileData.name,
            content: this.editedContent,
            isBinary: this.isBinary,
            mime: 'application/xml'
        };
        this.isLoading$.next(true);
        if (saveRes.mime === 'application/xml') {
            const result = XmlParserService.validateXML(saveRes.content);
            if (result === 'isXML') {
                this.save(saveRes);
            } else {
                this.notificationService.Error2(result);
            }
        } else {
            this.save(saveRes);
        }
        this.isEdit = false;
    }

    save(saveRes) {
        this.triggerService.editTrigger(saveRes)
            .subscribe(data => {
                    this.isLoading$.next(false);
                    this.notificationService.success('Save, and reindexing configuration');
                    this.dialogRef.close();
                },
                error => {
                    this.notificationService.Error(error.error);
                });
    }
    onAddTrigger(resCred: Credentials) {
        const dialogRef = this.dialog.open(AddTriggerComponent, {
            width: '50%',
            height: 'auto',
            maxHeight: '80%',
            data: {fileData: resCred}
        });
        dialogRef.afterClosed().subscribe(
            result => {
                this.triggerService.addTrigger(result)
                    .subscribe(
                        data => {
                            this.notificationService.success('Success');
                            this.isLoading$.next(false);
                            this.readContent(this.fullPath);
                        },
                        error => {
                            this.notificationService.Error(error.error);
                            console.log('Error');
                            this.isLoading$.next(false);
                        }
                    );
            }
        );
    }
    onClose() {
        this.dialogRef.close();
    }

}
