import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig} from '@angular/material';
import {CollectionsDialogComponent} from './collections-dialog/collections-dialog.component';
import {TriggersService} from './service/triggers.service';
import {XmlFileViewerComponent} from './xml-file-viewer/xml-file-viewer.component';
import {NotificationService} from '../error-notification-module/service/notification.service';
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
                        mime: '',
                        locked: '',
                        resource: false,
                        triggerConfigAvailable: false
                    };
                    this.collections = res;
                    this.collections.unshift(backElement);
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.Error(error.error);
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

    view(resCred: Credentials) {
        const dialogRef = this.dialog.open(XmlFileViewerComponent, {
            width: '80%',
            height: 'auto',
            maxHeight: '80%',
            data: {fileData: resCred}
        });
        dialogRef.afterClosed().subscribe();
    }

    backToRoot() {
        this.triggersRootConfigurationLocation = '/db/system/config/db';
        this.loadData(this.triggersRootConfigurationLocation);
    }

    onInitConf() {
        const dialogRef = this.dialog.open(CollectionsDialogComponent, {
            width: '80%',
            height: 'auto',
            maxHeight: '80%',
        });
        dialogRef.afterClosed().subscribe(
            data => {
                console.log(data);
                this.triggerService.initializeTriggerConfig(data)
                    .subscribe(
                        result => {
                            this.notificationService.success('Success');
                            this.loadData(this.triggersRootConfigurationLocation);
                        }, error => {
                            this.notificationService.Error(error.error);
                            this.loadData(this.triggersRootConfigurationLocation);
                        }
                    );
            }
        );
    }

}
