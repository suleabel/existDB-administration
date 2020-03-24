import {Component, Inject, OnInit} from '@angular/core';
import {StoreResourceModel} from '../model/StoreResourceModel';
import {FileExplorerService} from '../service/file-explorer.service';
import {Router} from '@angular/router';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BrowseSaveLocationComponent} from '../../xml-to-xsd/browse-save-location/browse-save-location.component';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';

@Component({
    selector: 'app-create-new-resource',
    templateUrl: './create-new-resource.component.html',
    styleUrls: ['./create-new-resource.component.sass']
})
export class CreateNewResourceComponent implements OnInit {
    public createResourceForm: FormGroup;
    public mimes = ['application/xml', 'application/xhtml+xml', 'application/x-javascript', 'text/css', 'text/text', 'application/less', 'application/json'];

    constructor(
        public dialogRef: MatDialogRef<CreateNewResourceComponent>,
        @Inject(MAT_DIALOG_DATA) public data,
        private formBuilder: FormBuilder,
        private dialog: MatDialog) {
    }

    ngOnInit() {
        this.buildForm();
    }

    buildForm() {
        this.createResourceForm = this.formBuilder.group({
            content: [''],
            mime: [''],
            url: [this.data.selectedPath, Validators.required],
            fileName: ['', Validators.required]
        });
    }

    browseURL() {
        const dialogRef = this.dialog.open(BrowseSaveLocationComponent, {
            width: '800px',
            height: '500px',
            data: {selectedPath: this.data.selectedPath}
        });
        dialogRef.afterClosed().subscribe(result => {
            this.data.selectedPath = result;
            this.buildForm();
        });
    }

    onClose() {
        this.dialogRef.close();
    }

    get url() {
        return this.createResourceForm.get('url');
    }

    get fileName() {
        return this.createResourceForm.get('fileName');
    }

    get mime() {
        return this.createResourceForm.get('mime');
    }
}
