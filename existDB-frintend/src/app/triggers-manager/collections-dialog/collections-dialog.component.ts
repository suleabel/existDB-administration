import {Component, Inject, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {TriggersService} from '../service/triggers.service';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {Credentials} from '../../collection-manager/model/Credentials';
import {FileExplorerService} from '../../collection-manager/service/file-explorer.service';

@Component({
    selector: 'app-collections-dialog',
    templateUrl: './collections-dialog.component.html',
    styleUrls: ['./collections-dialog.component.sass']
})
export class CollectionsDialogComponent implements OnInit {
    public selectedDirectory = '/db';
    public collections: Credentials[];
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date'];

    constructor(public dialogRef: MatDialogRef<CollectionsDialogComponent>,
                private triggerService: TriggersService,
                private fileExplorerService: FileExplorerService,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.loadData(this.selectedDirectory);
    }

    backToRoot() {
        this.selectedDirectory = '/db';
        this.loadData(this.selectedDirectory);
    }

    back() {
        console.log(this.selectedDirectory);
        const separatedString: string[] = this.selectedDirectory.split('/');
        separatedString.length = separatedString.length - 1;
        this.selectedDirectory = separatedString.join('/');
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

    loadData(path: string) {
        this.fileExplorerService.getOnlyCollections(path)
            .subscribe(data => {
                    const backElement = {
                        name: '..',
                        path: '',
                        owner: '',
                        group: '',
                        mode: '',
                        date: '',
                        mime: '',
                        locked: '',
                        resource: false,
                        triggerConfigAvailable: false
                    };
                    this.collections = data;
                    this.collections.unshift(backElement);
                },
                error => {
                    this.notificationService.warn(error.error.message);
                });
    }

    createTriggerHere() {
        this.triggerService.selectedCollection = this.selectedDirectory;
        this.triggerService.initializeTriggerConfig()
            .subscribe(
            data => {
                this.notificationService.success('Success');
            }, error => {
                this.notificationService.warn('Error: ' + error);
                }
        );
        this.dialogRef.close();
    }
    onClose() {
        this.triggerService.selectedCollection = undefined;
        this.dialogRef.close();
    }


}
