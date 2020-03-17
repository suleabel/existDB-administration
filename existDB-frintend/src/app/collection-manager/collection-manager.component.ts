import {Component, OnInit, ViewChild} from '@angular/core';
import {FileExplorerService} from './service/file-explorer.service';
import {Router} from '@angular/router';
import {Credentials} from './model/Credentials';
import {MatDialog, MatDialogConfig, MatSort, MatTableDataSource} from '@angular/material';
import {ResourceViewerDialogComponent} from './resource-viewer-dialog/resource-viewer-dialog.component';
import {NotificationService} from '../error-dialog/service/notification.service';
import {StoreResourceModel} from './model/StoreResourceModel';
import {CreateDirDialogComponent} from './create-dir-dialog/create-dir-dialog.component';
import {BehaviorSubject} from 'rxjs';

@Component({
    selector: 'app-collection-manager',
    templateUrl: './collection-manager.component.html',
    styleUrls: ['./collection-manager.component.sass']
})
export class CollectionManagerComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public selectedDirectory = '/db';
    public openedFile: string;
    public collections: any;
    public element: Credentials;
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date', 'writable', 'delete', 'view',
        'editCredentials'];

    constructor(private fileExplorerService: FileExplorerService,
                private router: Router,
                private dialog: MatDialog,
                private notificationService: NotificationService) {
    }
    // @ts-ignore
    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.loadData(this.selectedDirectory);
    }

    loadData(path: string) {
        this.isLoading$.next(true);
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
                        writable: false,
                        resource: false,
                        triggerConfigAvailable: false
                    };
                    this.collections = new MatTableDataSource();
                    this.collections.data = res;
                    this.collections.sort = this.sort;
                    this.collections.data.unshift(backElement);
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.warn('Error: ' + error.status);
                    this.backToRoot();
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
                    this.notificationService.warn('Error: ' + error);
                }
            );
        this.loadData(this.selectedDirectory);
    }

    deleteColl(res) {
        this.fileExplorerService.deleteCollection(res)
            .subscribe(data => {
                    this.notificationService.success('Success');
                    this.loadData(this.selectedDirectory);
                }, error => {
                    this.notificationService.warn('Error: ' + error);
                }
            );
        this.loadData(this.selectedDirectory);
    }

    editCredentials(selectedResourceCredentials) {
        this.fileExplorerService.setEditedFileCredentials(selectedResourceCredentials);
        this.router.navigateByUrl('/file-credentials');
    }

    createFileHere(currentDir: string) {
        this.fileExplorerService.setSaveContentHere(currentDir);
        this.router.navigateByUrl('/create-file');
    }

    createDir(currentDir: string) {
        this.fileExplorerService.setSaveContentHere(currentDir);
        const dialogRef = this.dialog.open(CreateDirDialogComponent, {
            width: '300px',
            height: '200px',
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === '') {
                alert('Directory name is empty!!');
            }
            const col: StoreResourceModel = {url: currentDir, fileName: result, content: null, isBinary: false};
            this.fileExplorerService.createDir(col)
                .subscribe(
                    data => {
                        this.notificationService.success('Success');
                        this.loadData(this.selectedDirectory);
                    },
                    error => {
                        this.notificationService.warn('Error: ' + error);
                    }
                );
        });
    }

    view(resCred: Credentials) {
        this.fileExplorerService.openedFile = resCred;
        const dialogDirNameConfig = new MatDialogConfig();
        dialogDirNameConfig.disableClose = true;
        dialogDirNameConfig.autoFocus = true;
        dialogDirNameConfig.width = '60%';
        this.dialog.open(ResourceViewerDialogComponent, dialogDirNameConfig);
    }
}
