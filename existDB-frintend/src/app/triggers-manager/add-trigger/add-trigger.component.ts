import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../../file-explorer/service/file-explorer.service';
import {EditTriggerModel} from '../model/EditTriggerModel';
import {DialogService} from '../../error-dialog/service/dialog.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'app-add-trigger',
    templateUrl: './add-trigger.component.html',
    styleUrls: ['./add-trigger.component.sass']
})
export class AddTriggerComponent implements OnInit {
    private triggerForm: FormGroup;
    private triggerEvents = ['create', 'update', 'copy', 'move', 'delete'];
    private triggerClass = 'org.exist.collections.triggers.XQueryTrigger';
    private triggerName  = ['url', 'query'];
    private triggerValue = 'xql url vagy xquery';
    private openedFile;

    constructor(public dialogRef: MatDialogRef<AddTriggerComponent>,
                private fileExplorerService: FileExplorerService,
                private dialogService: DialogService,
                private formBuilder: FormBuilder) {
    }

    ngOnInit() {
        this.openedFile = this.fileExplorerService.openedFile;

        this.triggerForm = this.formBuilder.group({
            event: [null, Validators.required],
            tClass: [this.triggerClass, Validators.required],
            name: [null, Validators.required],
            value: [this.triggerValue, Validators.required],
        });

    }

    onSave() {
        this.dialogService.openConfirmDialog('Are your sure to save this configuration ?')
            .afterClosed().subscribe(res => {
            if (res) {
                const trigCredCont: EditTriggerModel = {credentials: this.openedFile, event: '', tClass: '', name: '', value: ''};
                console.log(trigCredCont);
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
