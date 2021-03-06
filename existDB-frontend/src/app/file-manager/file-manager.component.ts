import {Component, OnInit, ViewChild} from '@angular/core';
import {FileManagerEntity} from './model/FileManagerEntity';
import {FileManagerService} from './service/file-manager.service';
import {BehaviorSubject} from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import {FileViewerDialogComponent} from './file-viewer-dialog/file-viewer-dialog.component';
import {MakeDirDialogComponent} from './make-dir-dialog/make-dir-dialog.component';
import {StoreDirOrFileModel} from './model/StoreDirOrFileModel';
import {CreateNewFileComponent} from './create-new-file/create-new-file.component';
import {NotificationService} from '../error-notification-module/service/notification.service';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';

@Component({
    selector: 'app-file-manager',
    templateUrl: './file-manager.component.html',
    styleUrls: ['./file-manager.component.sass']
})
export class FileManagerComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public TableData: any;
    public element: FileManagerEntity;
    // public content: FileManagerEntity[];
    public selectedDir: string;
    public root: string;
    public displayedColumns: string[] = ['name', 'isFile', 'size', 'humanSize', 'modified', 'hidden', 'canRead', 'canWrite', 'view', 'delete'];


    constructor(private fileManagerService: FileManagerService,
                private dialog: MatDialog,
                private notificationService: NotificationService) {
    }

    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.getRootDir();
    }

    loadData(url: string) {
        this.isLoading$.next(true);
        this.fileManagerService.getDirectoryContent(url)
            .subscribe(
                res => {
                    // const backElement = {
                    //     isFile: false,
                    //     name: '..',
                    //     size: '',
                    //     humanSize: '',
                    //     modified: '',
                    //     hidden: false,
                    //     canRead: true,
                    //     canWrite: true,
                    // };
                    this.TableData = new MatTableDataSource(res);
                    this.TableData.sort = this.sort;
                    // this.content.unshift(backElement);
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.Error(error.error);
                });
    }

    getRootDir() {
        this.fileManagerService.getRootDirectory()
            .subscribe(data => {
                    this.root = data.response;
                    this.selectedDir = this.root;
                    this.loadData(this.root);
                },
                error => {
                    this.notificationService.Error(error.error);
                });
    }

    backToRoot() {
        this.loadData(this.root);
    }

    aClick(col: string) {
        if (col === '..') {
            this.back();
        } else {
            this.selectedDir = this.selectedDir + '/' + col;
            this.loadData(this.selectedDir);
        }
    }

    back() {
        if (this.selectedDir !== this.root) {
            const separatedString: string[] = this.selectedDir.split('/');
            separatedString.length = separatedString.length - 1;
            this.selectedDir = separatedString.join('/');
            this.loadData(this.selectedDir);
        }
    }

    view(element: FileManagerEntity) {
        const dialogRef = this.dialog.open(FileViewerDialogComponent, {
            width: '80%',
            height: 'auto',
            maxHeight: '80%',
            data: {fileData: element, url: this.selectedDir}
        });
        dialogRef.afterClosed().subscribe();
    }

    newFile(selectedDir: string) {
        const dialogRef = this.dialog.open(CreateNewFileComponent, {
            width: '80%',
            height: '80%',
            data: {selectedPath: selectedDir}
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === '' || result === null || result === undefined) {
                this.notificationService.warn('Not created');
            } else {
                console.log(result);
                this.fileManagerService.serializeFile(result).subscribe(
                    data => {
                        this.notificationService.success('Success');
                        this.loadData(this.selectedDir);
                    }, error => {
                        this.notificationService.Error(error.error);
                    }
                );
            }
        });
    }

    delete(element) {
        const dir: StoreDirOrFileModel = {
            url: this.selectedDir,
            name: element.name
        };
        console.log(dir);
        this.fileManagerService.delete(dir).subscribe(
            data => {
                this.notificationService.success('Directory deleted');
                this.loadData(this.selectedDir);
            },
            error => {
                this.notificationService.Error(error.error);
            }
        );
    }

    makeDir(selectedDir: string) {
        const dialogRef = this.dialog.open(MakeDirDialogComponent, {
            width: 'auto',
            height: 'auto'
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === '' || result === null || result === undefined) {
                this.notificationService.warn('Collection name is empty');
            } else {
                const dir: StoreDirOrFileModel = {
                    url: selectedDir,
                    name: result
                };
                this.fileManagerService.makeDir(dir).subscribe(
                    data => {
                        this.loadData(this.selectedDir);
                        this.notificationService.success('Directory created');
                    },
                    error => {
                        this.notificationService.Error(error.error);
                    }
                );
            }
        });
    }
}
