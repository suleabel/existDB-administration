import {Component, Inject, OnInit} from '@angular/core';
import {FileExplorerService} from '../service/file-explorer.service';
import {Router} from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BrowseSaveLocationComponent} from '../../xml-to-xsd/browse-save-location/browse-save-location.component';

@Component({
    selector: 'app-create-file',
    templateUrl: './create-xquery.component.html',
    styleUrls: ['./create-xquery.component.sass']
})
export class CreateXqueryComponent implements OnInit {
    public createXqueryForm: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<CreateXqueryComponent>,
        @Inject(MAT_DIALOG_DATA) public data,
        private formBuilder: FormBuilder,
        private dialog: MatDialog) {
    }

    ngOnInit() {
        this.buildForm();
    }

    buildForm() {
        this.createXqueryForm = this.formBuilder.group({
            content: [''],
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
        return this.createXqueryForm.get('url');
    }

    get fileName() {
        return this.createXqueryForm.get('fileName');
    }

    get mime() {
        return this.createXqueryForm.get('mime');
    }
}
