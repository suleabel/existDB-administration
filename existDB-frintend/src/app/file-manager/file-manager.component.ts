import {Component, OnInit} from '@angular/core';
import {FileManagerEntity} from './model/FileManagerEntity';
import {FileManagerService} from './service/file-manager.service';
import {BehaviorSubject} from 'rxjs';
import {MatDialog} from '@angular/material';
import {FileViewerDialogComponent} from './file-viewer-dialog/file-viewer-dialog.component';
import {NotificationService} from '../error-dialog/service/notification.service';

@Component({
    selector: 'app-file-manager',
    templateUrl: './file-manager.component.html',
    styleUrls: ['./file-manager.component.sass']
})
export class FileManagerComponent implements OnInit {
    public content: FileManagerEntity[];
    public url: string;
    public root: string;

    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public displayedColumns: string[] = ['name', 'isFile', 'size', 'humanSize', 'modified', 'hidden', 'canRead', 'canWrite', 'view'];

    constructor(private fileManagerService: FileManagerService,
                private dialog: MatDialog,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.getRootDir();
    }

    loadData(url: string) {
        this.isLoading$.next(true);
        this.fileManagerService.getDirectoryContent(url)
            .subscribe(
                res => {
                    const backElement = {
                        isFile: false,
                        name: '..',
                        size: '',
                        humanSize: '',
                        modified: '',
                        hidden: false,
                        canRead: true,
                        canWrite: true,
                    };
                    this.content = res;
                    this.content.unshift(backElement);
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.Error(error.error);
                });
    }

    getRootDir() {
        this.fileManagerService.getRootDirectory()
            .subscribe(data => {
                    console.log(data);
                    this.root = data;
                    this.url = this.root;
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
        console.log(col);
        if (col === '..') {
            this.back();
        } else {
            this.url = this.url + '/' + col;
            this.loadData(this.url);
        }
    }

    back() {
        if (this.url !== this.root) {
            const separatedString: string[] = this.url.split('/');
            separatedString.length = separatedString.length - 1;
            this.url = separatedString.join('/');
            this.loadData(this.url);
        }
    }

    view(element: FileManagerEntity) {
        const dialogRef = this.dialog.open(FileViewerDialogComponent, {
            width: '80%',
            height: '80%',
            data: {fileData: element, url: this.url}
        });
        dialogRef.afterClosed().subscribe( result => {
           console.log(result);
        });
    }

}
