import {Component, OnInit, ViewChild} from '@angular/core';
import {FileExplorerService} from './service/file-explorer.service';
import {Router} from '@angular/router';
import {Credentials} from './model/Credentials';
import {MatDialog} from '@angular/material/dialog';
import {ResourceViewerDialogComponent} from './resource-viewer-dialog/resource-viewer-dialog.component';
import {NotificationService} from '../error-notification-module/service/notification.service';
import {StoreResourceModel} from './model/StoreResourceModel';
import {CreateDirDialogComponent} from './create-dir-dialog/create-dir-dialog.component';
import {BehaviorSubject} from 'rxjs';
import {CreateNewResourceComponent} from './create-new-resource/create-new-resource.component';
import {CreateXqueryComponent} from './create-xquery/create-xquery.component';
import {FileCredentialsComponent} from './file-credentials/file-credentials.component';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';

@Component({
    selector: 'app-collection-manager',
    templateUrl: './collection-manager.component.html',
    styleUrls: ['./collection-manager.component.sass']
})
export class CollectionManagerComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public selectedDirectory = '/db';
    public collections: any;
    public element: Credentials;
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date', 'delete', 'view',
        'editCredentials'];

    constructor(private fileExplorerService: FileExplorerService,
                private router: Router,
                private dialog: MatDialog,
                private notificationService: NotificationService) {
    }

    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.loadData(this.selectedDirectory);
    }

    loadData(path: string) {
        this.isLoading$.next(true);
        this.fileExplorerService.getCollection(path)
            .subscribe(
                res => {
                    this.collections = new MatTableDataSource(res);
                    this.collections.sort = this.sort;
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.Error(error.error);
                });
    }

    aClick(col: string) {
        if (col === '..') {
            this.back();
        } else {
            this.selectedDirectory = this.selectedDirectory + '/' + col;
            this.loadData(this.selectedDirectory);
        }
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

    deleteRes(res) {
        this.fileExplorerService.deleteResource(res)
            .subscribe(data => {
                    this.notificationService.success('Success');
                    this.loadData(this.selectedDirectory);
                }, error => {
                this.notificationService.Error(error.error);
                this.loadData(this.selectedDirectory);
                }
            );
    }

    deleteColl(res) {
        this.fileExplorerService.deleteCollection(res)
            .subscribe(data => {
                    this.notificationService.success('Success');
                    this.loadData(this.selectedDirectory);
                }, error => {
                this.notificationService.Error(error.error);
                this.loadData(this.selectedDirectory);
                }
            );
    }

    editCredentials(selectedResourceCredentials) {
        const dialogRef = this.dialog.open(FileCredentialsComponent, {
            width: '50%',
            height: 'auto',
            data: {res: selectedResourceCredentials}
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === '' || result === null || result === undefined) {
                this.notificationService.success('Not created');
            } else {
                const editFileData: Credentials = result;
                this.fileExplorerService.editResCredentials(editFileData)
                    .subscribe(data => {
                            this.notificationService.success('Success');
                        },
                        error => {
                            this.notificationService.Error(error.error);
                        });
            }
        });
    }

    newXquery(currentDir: string) {
        const dialogRef = this.dialog.open(CreateXqueryComponent, {
            width: '80%',
            height: '80%',
            data: {selectedPath: currentDir}
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === '' || result === null || result === undefined) {
                this.notificationService.warn('Not created');
            } else {
                const saveData: StoreResourceModel = result;
                saveData.isBinary = true;
                saveData.mime = 'application/xquery';
                console.log(saveData);
                this.fileExplorerService.saveResource(saveData)
                    .subscribe(
                        data => {
                            this.notificationService.success('Success');
                            this.loadData(this.selectedDirectory);
                        }, error => {
                            this.notificationService.Error(error.error);
                        });
            }
        });
    }

    newResource(currentDir: string) {
        const dialogRef = this.dialog.open(CreateNewResourceComponent, {
            width: '80%',
            height: '80%',
            data: {selectedPath: currentDir}
        });
        dialogRef.afterClosed().subscribe(result => {
                if (result === '' || result === null || result === undefined) {
                    this.notificationService.warn('Not created');
                } else {
                    const saveData: StoreResourceModel = result;
                    saveData.isBinary = false;
                    console.log(saveData);
                    this.fileExplorerService.saveResource(saveData)
                        .subscribe(
                            data => {
                                this.notificationService.success('Success');
                                this.loadData(this.selectedDirectory);
                            }, error => {
                                this.notificationService.Error(error.error);
                            });
                }
            }
        );
    }

    createDir(currentDir: string) {
        const dialogRef = this.dialog.open(CreateDirDialogComponent, {
            width: 'auto',
            height: 'auto'
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === '' || result === null || result === undefined) {
                this.notificationService.warn('Collection name is empty');
            } else {
                const col: StoreResourceModel = {
                    url: currentDir,
                    fileName: result,
                    content: null,
                    isBinary: false,
                    mime: ''
                };
                this.fileExplorerService.createDir(col)
                    .subscribe(
                        data => {
                            this.notificationService.success('Success');
                            this.loadData(this.selectedDirectory);
                        },
                        error => {
                            this.notificationService.Error(error.error);
                        }
                    );
            }
        });
    }

    view(resCred: Credentials) {
        const dialogRef = this.dialog.open(ResourceViewerDialogComponent, {
            width: '80%',
            height: 'auto',
            maxHeight: '80%',
            data: {fileData: resCred, url: this.selectedDirectory}
        });
        dialogRef.afterClosed().subscribe();
    }
}
