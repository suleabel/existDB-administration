import {Component, OnInit, ViewChild} from '@angular/core';
import {CollectionsDialogComponent} from './collections-dialog/collections-dialog.component';
import {TriggersService} from './service/triggers.service';
import {XmlFileViewerComponent} from './xml-file-viewer/xml-file-viewer.component';
import {NotificationService} from '../error-notification-module/service/notification.service';
import {BehaviorSubject} from 'rxjs';
import {Credentials} from '../collection-manager/model/Credentials';
import {FileExplorerService} from '../collection-manager/service/file-explorer.service';
import {MatDialog} from '@angular/material/dialog';
import {MatTableDataSource} from '@angular/material/table';

@Component({
    selector: 'app-triggers-manager',
    templateUrl: './triggers-manager.component.html',
    styleUrls: ['./triggers-manager.component.sass']
})
export class TriggersManagerComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    private triggersRootConfigurationLocation = '/db/system/config/db';
    public TableData: any;
    public element: Credentials;
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date'];

    constructor(
        private dialog: MatDialog,
        private triggerService: TriggersService,
        private fileExplorerService: FileExplorerService,
        private notificationService: NotificationService) {
    }

    // @ts-ignore
    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.loadData(this.triggersRootConfigurationLocation);
    }

    loadData(path: string) {
        this.isLoading$.next(true);
        this.triggerService.getTriggerConfigs(path)
            .subscribe(
                res => {
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
                    this.TableData = new MatTableDataSource(res);
                    this.TableData.sort = this.sort;
                    // this.collections.unshift(backElement);
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
                if (data === null || data === undefined || data === '') {
                    this.notificationService.success('No selected collection');
                } else {

                    this.isLoading$.next(true);
                    this.triggerService.initializeTriggerConfig(data)
                        .subscribe(
                            result => {
                                this.notificationService.success('Success');
                                this.isLoading$.next(false);
                                this.loadData(this.triggersRootConfigurationLocation);
                            }, error => {
                                this.notificationService.Error(error.error);
                                this.isLoading$.next(false);
                                this.loadData(this.triggersRootConfigurationLocation);
                            }
                        );
                }

            }
        );
    }

}
