import {Component, OnInit, ViewChild} from '@angular/core';
import {TriggersService} from '../service/triggers.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {Credentials} from '../../collection-manager/model/Credentials';
import {FileExplorerService} from '../../collection-manager/service/file-explorer.service';
import {BehaviorSubject} from 'rxjs';
import {MatDialogRef} from '@angular/material/dialog';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';

@Component({
    selector: 'app-collections-dialog',
    templateUrl: './collections-dialog.component.html',
    styleUrls: ['./collections-dialog.component.sass']
})
export class CollectionsDialogComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public selectedDirectory = '/db';
    public element: Credentials;
    public TableData: any;
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date'];

    constructor(public dialogRef: MatDialogRef<CollectionsDialogComponent>,
                private triggerService: TriggersService,
                private fileExplorerService: FileExplorerService,
                private notificationService: NotificationService) {
    }

    @ViewChild(MatSort) sort: MatSort;

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
        this.isLoading$.next(true);
        this.fileExplorerService.getOnlyCollections(path)
            .subscribe(data => {
                    // const backElement = {
                    //     name: '..',
                    //     path: '',
                    //     owner: '',
                    //     group: '',
                    //     mode: '',
                    //     date: '',
                    //     mime: '',
                    //     locked: '',
                    //     resource: false,
                    //     triggerConfigAvailable: false
                    // };
                    this.TableData = new MatTableDataSource(data);
                    this.TableData.sort = this.sort;
                    this.isLoading$.next(false);
                    // this.collections.unshift(backElement);
                },
                error => {
                    this.notificationService.Error(error.error);
                });
    }

    onClose() {
        this.dialogRef.close();
    }


}
