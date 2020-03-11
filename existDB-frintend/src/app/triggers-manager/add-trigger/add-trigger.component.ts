import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../../file-explorer/service/file-explorer.service';
import {EditTriggerModel} from '../model/EditTriggerModel';
import {DialogService} from '../../error-dialog/service/dialog.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TriggersService} from '../service/triggers.service';
import {NotificationService} from '../../error-dialog/service/notification.service';

@Component({
    selector: 'app-add-trigger',
    templateUrl: './add-trigger.component.html',
    styleUrls: ['./add-trigger.component.sass']
})
export class AddTriggerComponent implements OnInit {
    public triggerForm: FormGroup;
    public triggerEvents = ['create', 'update', 'copy', 'move', 'delete'];
    public triggerClass = 'org.exist.collections.triggers.XQueryTrigger';
    public triggerName = ['url', 'query'];
    public triggerValue = 'test.xql';
    public openedFile;
    public query: string;

    constructor(public dialogRef: MatDialogRef<AddTriggerComponent>,
                private fileExplorerService: FileExplorerService,
                private notificationService: NotificationService,
                private triggerService: TriggersService,
                private dialogService: DialogService,
                private formBuilder: FormBuilder,
                private dialog: MatDialog) {
    }

    ngOnInit() {
        this.openedFile = this.fileExplorerService.openedFile;
        console.log(this.openedFile);

        this.triggerForm = this.formBuilder.group({
            event: [null, Validators.required],
            tClass: [this.triggerClass, Validators.required],
            name: ['url', Validators.required],
            value: [this.triggerValue, Validators.required],
        });

    }

    onSave() {
        this.dialogService.openConfirmDialog('Are your sure to save this configuration ?')
            .afterClosed().subscribe(res => {
            if (res) {
                const trigCredCont: EditTriggerModel = {
                    path: this.openedFile.path, fName: this.openedFile.name, event: this.triggerForm.value.event, tClass: this.triggerForm.value.tClass,
                    name: this.triggerForm.value.name, value: this.triggerForm.value.value
                };
                this.triggerService.addTrigger(trigCredCont)
                    .subscribe(
                        data => {
                            this.notificationService.success('Success: ' + data);
                            this.dialogRef.close();
                        },
                        error => {
                            this.notificationService.warn('Error: ' + error);
                            this.dialogRef.close();
                        }
                    );
            }
        });
    }

    onClose() {
        this.dialogRef.close();
    }


    get event() {
        return this.triggerForm.get('event');
    }

    get tClass() {
        return this.triggerForm.get('tClass');
    }

    get name() {
        return this.triggerForm.get('name');
    }

    get value() {
        return this.triggerForm.get('value');
    }
}
