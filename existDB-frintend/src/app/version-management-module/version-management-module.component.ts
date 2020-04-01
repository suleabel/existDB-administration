import {Component, OnInit} from '@angular/core';
import {VersionManagementService} from './service/version-management.service';
import {NotificationService} from '../error-dialog/service/notification.service';
import {BehaviorSubject} from 'rxjs';
import {MatDialog} from '@angular/material';
import {Credentials} from '../collection-manager/model/Credentials';
import {FileExplorerService} from '../collection-manager/service/file-explorer.service';
import {ViewHistoryComponent} from './view-history/view-history.component';

@Component({
    selector: 'app-version-management-module',
    templateUrl: './version-management-module.component.html',
    styleUrls: ['./version-management-module.component.sass']
})
export class VersionManagementModuleComponent implements OnInit {
    public versionIsAvailable$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public selectedDirectory = '/db';
    public collections: Credentials[];
    public displayedColumns: string[] = ['name', 'resource', 'view'];

    constructor(private versionManagementService: VersionManagementService,
                private notificationService: NotificationService,
                private fileExplorerService: FileExplorerService,
                private dialog: MatDialog) {
    }

    ngOnInit() {
        this.checkVersionManagementIsEnabled();
        if (this.versionIsAvailable$) {
            this.loadData(this.selectedDirectory);
        }
    }

    private checkVersionManagementIsEnabled() {
        this.versionIsAvailable$.next(false);
        this.versionManagementService.versionManagerIsActivated()
            .subscribe(data => {
                this.versionIsAvailable$.next(data);
            }, error => {
                if (error.error.message !== 'no error message') {
                    this.notificationService.Error(error.error);
                } else {
                    console.log(error.error);
                    this.checkVersionManagementIsEnabled();
                }
            });
    }

    private activateVersionManagement() {
        this.versionManagementService.enableVersionManager()
            .subscribe(data => {
                    this.notificationService.success('Version Management is enabled');
                    this.checkVersionManagementIsEnabled();
                },
                error => {
                    this.notificationService.Error(error.error);
                });
    }

    private loadData(path: string) {
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
                        locked: '',
                        writable: false,
                        resource: false,
                        triggerConfigAvailable: false
                    };
                    this.collections = res;
                    this.collections.unshift(backElement);
                    // console.log('content: ' + this.collections);
                },
                error => {
                    if (error.error.message !== 'no error message') {
                        this.notificationService.Error(error.error);
                    } else {
                        console.log(error.error);
                        this.loadData(path);
                    }
                    this.backToRoot();
                });
    }

    private aClick(col: string) {
        if (col === '..') {
            this.back();
        } else {
            this.selectedDirectory = this.selectedDirectory + '/' + col;
            this.loadData(this.selectedDirectory);
        }
    }

    private back() {
        console.log(this.selectedDirectory);
        if (this.selectedDirectory !== '/db') {
            const separatedString: string[] = this.selectedDirectory.split('/');
            separatedString.length = separatedString.length - 1;
            this.selectedDirectory = separatedString.join('/');
            this.loadData(this.selectedDirectory);
        }
    }

    private backToRoot() {
        this.selectedDirectory = '/db';
        this.loadData(this.selectedDirectory);
    }

    private getVersions(element) {
        console.log(element.name);
        if (element.name.includes('xml')) {
            const dialogRef = this.dialog.open(ViewHistoryComponent, {
                width: '60%',
                height: 'auto',
                data: {res: element}
            });
            dialogRef.afterClosed().subscribe(result => {
                console.log(result);
            });
        } else {
            this.notificationService.warn('This is not XML document!!');
        }
    }

    // private deactivateVersionManagement() {
    //     window.location.reload();
    // }

}
