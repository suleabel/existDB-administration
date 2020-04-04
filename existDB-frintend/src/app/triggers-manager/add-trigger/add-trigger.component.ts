import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {EditTriggerModel} from '../model/EditTriggerModel';
import {DialogService} from '../../error-notification-module/service/dialog.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TriggersService} from '../service/triggers.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {FileExplorerService} from '../../collection-manager/service/file-explorer.service';
import {BrowseXqueryFileComponent} from '../browse-xquery-file/browse-xquery-file.component';
import {BehaviorSubject} from 'rxjs';

@Component({
    selector: 'app-add-trigger',
    templateUrl: './add-trigger.component.html',
    styleUrls: ['./add-trigger.component.sass']
})
export class AddTriggerComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public triggerForm: FormGroup;
    public triggerEvents = ['create', 'update', 'copy', 'move', 'delete'];
    public triggerName = ['url', 'query'];
    public triggerClass = 'org.exist.collections.triggers.XQueryTrigger';
    public tempFormData = {event: '', tClass: this.triggerClass, name: 'url', value: ''};
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
        this.buildForm(this.tempFormData);
    }

    buildForm(tempFormData) {
        this.triggerForm = this.formBuilder.group({
            event: [tempFormData.event],
            tClass: [tempFormData.tClass, Validators.required],
            name: [tempFormData.name, Validators.required],
            value: [tempFormData.value, Validators.required],
            overwrite: [false]
        });
    }

    browseXquery() {
        this.tempFormData = this.triggerForm.value;
        const dialogRef = this.dialog.open(BrowseXqueryFileComponent, {
            width: '70%',
            height: '70%',
            data: {}
        });
        dialogRef.afterClosed().subscribe(result => {
            this.tempFormData.value = result;
            this.buildForm(this.tempFormData);
        });
    }

    onSave() {
        this.dialogService.openConfirmDialog('Are your sure to save this configuration ?')
            .afterClosed().subscribe(res => {
            this.isLoading$.next(true);
            if (res) {
                const trigCredCont: EditTriggerModel = {
                    path: this.openedFile.path,
                    fName: this.openedFile.name,
                    event: this.triggerForm.value.event,
                    tClass: this.triggerForm.value.tClass,
                    name: this.triggerForm.value.name,
                    value: this.triggerForm.value.value,
                    isOverwrite: this.triggerForm.value.overwrite.toString(),
                };
                console.log(trigCredCont);
                this.triggerService.addTrigger(trigCredCont)
                    .subscribe(
                        data => {
                            this.notificationService.success('Success');
                            console.log('success');
                            this.isLoading$.next(false);
                            this.dialogRef.close();
                        },
                        error => {
                            this.notificationService.Error(error.error);
                            console.log('Error');
                            this.isLoading$.next(false);
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
