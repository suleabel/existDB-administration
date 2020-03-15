import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {CollectionsDialogComponent} from './collections-dialog/collections-dialog.component';
import {TriggersService} from './service/triggers.service';
import {XmlFileViewerComponent} from './xml-file-viewer/xml-file-viewer.component';
import {NotificationService} from '../error-dialog/service/notification.service';
import {BehaviorSubject} from 'rxjs';
import {Credentials} from '../collection-manager/model/Credentials';
import {FileExplorerService} from '../collection-manager/service/file-explorer.service';

@Component({
    selector: 'app-triggers-manager',
    templateUrl: './triggers-manager.component.html',
    styleUrls: ['./triggers-manager.component.sass']
})
export class TriggersManagerComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    private triggersRootConfigurationLocation = '/db/system/config/db';
    public collections: Credentials[];
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date'];

    constructor(
        private dialog: MatDialog,
        private triggerService: TriggersService,
        private fileExplorerService: FileExplorerService,
        private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.loadData(this.triggersRootConfigurationLocation);
    }

    loadData(path: string) {
        this.isLoading$.next(true);
        this.triggerService.getTriggerConfigs(path)
            .subscribe(
                res => {
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
                    this.collections = res;
                    this.collections.unshift(backElement);
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.warn('Error: ' + error);
                    // this.backToRoot();
                });
    }

    aClick(col: string) {
        if (col === '..') {
            this.back();
        } else {
            this.triggersRootConfigurationLocation = this.triggersRootConfigurationLocation + '/' + col;
            this.loadData(this.triggersRootConfigurationLocation);
        }
    }

    back() {
        console.log(this.triggersRootConfigurationLocation);
        if (this.triggersRootConfigurationLocation !== '/db/system/config/db') {
            const separatedString: string[] = this.triggersRootConfigurationLocation.split('/');
            separatedString.length = separatedString.length - 1;
            this.triggersRootConfigurationLocation = separatedString.join('/');
            this.loadData(this.triggersRootConfigurationLocation);
        }
    }

    view(resName: Credentials) {
        console.log(resName);
        this.fileExplorerService.openedFile = resName;
        const dialogDirNameConfig = new MatDialogConfig();
        dialogDirNameConfig.disableClose = true;
        dialogDirNameConfig.autoFocus = true;
        dialogDirNameConfig.width = '60%';
        this.dialog.open(XmlFileViewerComponent, dialogDirNameConfig);
    }

    backToRoot() {
        this.triggersRootConfigurationLocation = '/db/system/config/db';
        this.loadData(this.triggersRootConfigurationLocation);
    }

    openDialog() {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.width = '60%';
        this.dialog.open(CollectionsDialogComponent, dialogConfig);
    }

    getSelectedCollection() {
        console.log(this.triggerService.selectedCollection);
    }

}
