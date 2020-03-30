import {Component, OnInit} from '@angular/core';
import {XmlToXsdService} from './service/xml-to-xsd.service';
import {DomSanitizer} from '@angular/platform-browser';
import {MatDialog} from '@angular/material/dialog';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BrowseSaveLocationComponent} from './browse-save-location/browse-save-location.component';
import {NotificationService} from '../error-dialog/service/notification.service';
import {StoreResourceModel} from '../collection-manager/model/StoreResourceModel';

@Component({
    selector: 'app-xml-to-xsd',
    templateUrl: './xml-to-xsd.component.html',
    styleUrls: ['./xml-to-xsd.component.sass']
})
export class XmlToXsdComponent implements OnInit {
    public saveForm: FormGroup;
    public saveData: StoreResourceModel;
    public generatedXSD = '';
    public textValue = '';
    public fileUrl;

    public selectedPath: string;

    constructor(private xmlService: XmlToXsdService,
                private sanitizer: DomSanitizer,
                private formBuilder: FormBuilder,
                private dialog: MatDialog,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.buildForm();
    }

    buildForm() {
        this.saveForm = this.formBuilder.group({
            url: [this.selectedPath, Validators.required],
            fileName: ['', Validators.required]
        });
    }

    get url() {
        return this.saveForm.get('url');
    }

    get fileName() {
        return this.saveForm.get('fileName');
    }

    browseURL() {
        const dialogRef = this.dialog.open(BrowseSaveLocationComponent, {
            width: '800px',
            height: '500px',
            data: {selectedPath: this.selectedPath}
        });
        dialogRef.afterClosed().subscribe(result => {
            this.selectedPath = result;
            this.buildForm();
        });
    }

    sendText(value: string): void {
        console.log(value);
        this.xmlService.sendXml(this.textValue).subscribe(data => {
            this.generatedXSD = data;
        }, error => {
            this.notificationService.Error(error.error);
        });
    }

    saveXsdToDB() {
        this.saveData = this.saveForm.value;
        this.saveData.content = this.generatedXSD;
        this.xmlService.saveXsd(this.saveData).subscribe(data => {
                console.log(data);
            },
            error => {
                console.log(error);
            });
    }

    downloadXsd() {
        const blob = new Blob([this.generatedXSD], {type: 'application/xml'});
        this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
    }

}
