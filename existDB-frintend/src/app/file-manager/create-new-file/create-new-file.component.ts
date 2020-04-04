import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {XmlParserService} from '../../collection-manager/service/xml-parser.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {SerializeFileModel} from '../model/SerializeFileModel';

@Component({
    selector: 'app-create-new-file',
    templateUrl: './create-new-file.component.html',
    styleUrls: ['./create-new-file.component.sass']
})
export class CreateNewFileComponent implements OnInit {
    public createFileForm: FormGroup;
    public types = ['xml', 'other'];

    constructor(
        public dialogRef: MatDialogRef<CreateNewFileComponent>,
        @Inject(MAT_DIALOG_DATA) public data,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private xmlParser: XmlParserService,
        private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.buildForm();
    }

    buildForm() {
        this.createFileForm = this.formBuilder.group({
            content: [''],
            type: ['', Validators.required],
            url: [this.data.selectedPath, Validators.required],
            fileName: ['', Validators.required]
        });
    }

    browseURL() {
        // TODO egyszer talán megcsinálom
    }

    save() {
        const data: SerializeFileModel = {
            content: this.createFileForm.value.content,
            parameter: '',
            path: this.data.selectedPath,
            isXml: 'false',
            name: this.createFileForm.value.fileName
        };
        if (this.createFileForm.value.type === 'xml') {
            const result = XmlParserService.validateXML(this.createFileForm.value.content);
            if (result === 'isXML') {
                data.isXml = 'true';
                this.dialogRef.close(data);
            } else {
                this.notificationService.Error2(result);
            }
        } else {
            this.dialogRef.close(data);
        }
    }

    onClose() {
        this.dialogRef.close();
    }

    get url() {
        return this.createFileForm.get('url');
    }

    get fileName() {
        return this.createFileForm.get('fileName');
    }

    get type() {
        return this.createFileForm.get('type');
    }
}
