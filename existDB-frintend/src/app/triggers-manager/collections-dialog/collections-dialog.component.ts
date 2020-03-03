import {Component, Inject, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {TriggersService} from '../service/triggers.service';
import {FileExplorerService} from '../../file-explorer/service/file-explorer.service';
import {Credentials} from '../../file-explorer/model/Credentials';

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
                private fileExplorerService: FileExplorerService) {
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
                        writable: false,
                        resource: false,
                        triggerConfigAvailable: false
                    };
                    this.collections = data;
                    this.collections.unshift(backElement);
                    console.log(this.collections);
                },
                error => {
                    console.log(error);
                });
    }

    createTriggerHere() {
        this.triggerService.selectedCollection = this.selectedDirectory;
        this.triggerService.initializeTriggerConfig()
            .subscribe(
            data => {
                console.log(data);
            }, error => {
                console.log(error);
                }
        );
        this.dialogRef.close();
    }
    onClose() {
        this.triggerService.selectedCollection = undefined;
        this.dialogRef.close();
    }


}
