import {Component, OnInit} from '@angular/core';
import {FileExplorerService} from '../../collection-manager/service/file-explorer.service';
import {MatDialogRef} from '@angular/material';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {BrowseSaveLocationComponent} from '../../xml-to-xsd/browse-save-location/browse-save-location.component';
import {Credentials} from '../../collection-manager/model/Credentials';

@Component({
    selector: 'app-browse-xquery-file',
    templateUrl: './browse-xquery-file.component.html',
    styleUrls: ['./browse-xquery-file.component.sass']
})
export class BrowseXqueryFileComponent implements OnInit {
    public selectedDirectory = '/db';
    public collections: Credentials[];
    public displayedColumns: string[] = ['name', 'resource'];

    constructor(
        private fileExplorerService: FileExplorerService,
        private dialogRef: MatDialogRef<BrowseSaveLocationComponent>,
        private notificationService: NotificationService
    ) {
    }

    ngOnInit() {
        this.loadData(this.selectedDirectory);
    }

    loadData(path: string) {
        this.fileExplorerService.getCollection(path)
            .subscribe(
                res => {
                    const backElement = {
                        name: '..',
                        path: '',
                        owner: '',
                        group: '',
                        mode: '',
                        date: '',
                        mime: '',
                        resource: false,
                        triggerConfigAvailable: false
                    };
                    this.collections = res;
                    this.collections.unshift(backElement);
                },
                error => {
                    this.notificationService.warn('Error: ' + error);
                    this.backToRoot();
                });
    }

    select(element: Credentials) {
        this.dialogRef.close(this.selectedDirectory + '/' + element.name);
    }

    back() {
        console.log(this.selectedDirectory);
        if (this.selectedDirectory !== '/db') {
            const separatedString: string[] = this.selectedDirectory.split('/');
            separatedString.length = separatedString.length - 1;
            this.selectedDirectory = separatedString.join('/');
            this.loadData(this.selectedDirectory);
        }
    }

    backToRoot() {
        this.selectedDirectory = '/db';
        this.loadData(this.selectedDirectory);
    }

    aClick(col: string) {
        if (col === '..') {
            this.back();
        } else {
            this.selectedDirectory = this.selectedDirectory + '/' + col;
            this.loadData(this.selectedDirectory);
        }
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

}
