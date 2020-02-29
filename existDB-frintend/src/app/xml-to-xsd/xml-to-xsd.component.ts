import {Component, OnInit} from '@angular/core';
import {XmlToXsdService} from './service/xml-to-xsd.service';
import {DomSanitizer} from '@angular/platform-browser';
import {MatDialog} from '@angular/material/dialog';
import {DialogPanelComponent} from './dialog-panel/dialog-panel.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SaveModel} from './model/SaveXSDModel';

@Component({
    selector: 'app-xml-to-xsd',
    templateUrl: './xml-to-xsd.component.html',
    styleUrls: ['./xml-to-xsd.component.sass']
})
export class XmlToXsdComponent implements OnInit {
    private saveForm: FormGroup;
    private saveData: SaveModel;
    private generatedXSD = '';
    private textValue = '';
    private fileUrl;

    animal: string;
    name: string;

    constructor(private xmlService: XmlToXsdService,
                private sanitizer: DomSanitizer,
                private formBuilder: FormBuilder,
                public dialog: MatDialog) {
    }

    ngOnInit() {
        this.saveForm = this.formBuilder.group({
            url: ['/db/generated-xml-schemas', Validators.required],
            fileName: ['', Validators.required]
        });
    }

    get url() {
        return this.saveForm.get('url');
    }

    get fileName() {
        return this.saveForm.get('fileName');
    }

    openDialog(): void {
        const dialogRef = this.dialog.open(DialogPanelComponent, {
            width: '250px',
            height: '500px',
            data: {name: this.name, animal: this.animal}
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
            this.animal = result;
        });
    }

    sendText(value: string): void {
        console.log(value);
        this.xmlService.sendXml(this.textValue).subscribe(data => {
            this.generatedXSD = data;
        }, error => {
            console.log(error);
        });
    }

    saveXsdToDB() {
        this.saveData = this.saveForm.value;
        this.saveData.content = this.generatedXSD;
        console.log(this.saveData);
        this.xmlService.saveXsd(this.saveData).subscribe(data => {
                console.log(data);
            },
            error => {
                console.log(error);
            });
    }

    downloadXsd() {
        console.log(this.generatedXSD);
        const blob = new Blob([this.generatedXSD], {type: 'application/xml'});
        this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
    }

}
