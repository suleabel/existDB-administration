import {Component, OnInit, ViewChild} from '@angular/core';
import {VersionManagementService} from './service/version-management.service';
import {NotificationService} from '../error-notification-module/service/notification.service';
import {BehaviorSubject} from 'rxjs';
import {Credentials} from '../collection-manager/model/Credentials';
import {FileExplorerService} from '../collection-manager/service/file-explorer.service';
import {ViewHistoryComponent} from './view-history/view-history.component';
import {MatDialog} from '@angular/material/dialog';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';

@Component({
    selector: 'app-version-management-module',
    templateUrl: './version-management-module.component.html',
    styleUrls: ['./version-management-module.component.sass']
})
export class VersionManagementModuleComponent implements OnInit {
    public versionIsAvailable$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public isLoading2$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public selectedDirectory = '/db';
    public element: Credentials;
    public TableData: any;
    public displayedColumns: string[] = ['name', 'resource', 'view'];

    constructor(private versionManagementService: VersionManagementService,
                private notificationService: NotificationService,
                private fileExplorerService: FileExplorerService,
                private dialog: MatDialog) {
    }

    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.checkVersionManagementIsEnabled();
    }

    private checkVersionManagementIsEnabled() {
        this.isLoading$.next(true);
        this.versionManagementService.versionManagerIsActivated()
            .subscribe(data => {
                if (data.response === 'true') {
                    this.versionIsAvailable$.next(true);
                    if (this.versionIsAvailable$.value) {
                        this.loadData(this.selectedDirectory);
                    }
                }
                this.isLoading$.next(false);
            }, error => {
                this.isLoading$.next(false);
                if (error.error.message !== 'no error message') {
                    this.notificationService.Error(error.error);
                } else {
                    console.log(error.error);
                    // TODO dodgy exception solution
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
        this.isLoading2$.next(true);
        this.fileExplorerService.getCollection(path)
            .subscribe(
                res => {
                    this.TableData = new MatTableDataSource(res);
                    this.TableData.sort = this.sort;
                    this.isLoading2$.next(false);
                },
                error => {
                    // TODO dodgy exception mask
                    if (error.error.message !== 'no error message') {
                        this.notificationService.Error(error.error);
                    } else {
                        this.loadData(path);
                    }
                    this.isLoading2$.next(false);
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
        if (element.name.includes('xml')) {
            const dialogRef = this.dialog.open(ViewHistoryComponent, {
                width: '60%',
                height: 'auto',
                maxHeight: '80%',
                data: {res: element}
            });
            dialogRef.afterClosed().subscribe(result => {
                console.log(result);
            });
        } else {
            this.notificationService.warn('This is not XML document!!');
        }
    }

}
