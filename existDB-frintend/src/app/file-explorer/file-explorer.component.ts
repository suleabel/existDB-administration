import {Component, OnInit} from '@angular/core';
import {FileExplorerService} from './service/file-explorer.service';
import {Router} from '@angular/router';
import {Credentials} from './model/Credentials';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {CreateDirDialogComponent} from './create-dir-dialog/create-dir-dialog.component';
import {FileViewerDialogComponent} from './file-viewer-dialog/file-viewer-dialog.component';

@Component({
    selector: 'app-file-explorer',
    templateUrl: './file-explorer.component.html',
    styleUrls: ['./file-explorer.component.sass']
})
export class FileExplorerComponent implements OnInit {
    public selectedDirectory = '/db';
    public openedFile: string;
    public collections: Credentials[];
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date', 'writable', 'delete', 'view',
        'editCredentials'];

    constructor(private fileExplorerService: FileExplorerService, private router: Router, private dialog: MatDialog) {
    }

    ngOnInit() {
        this.loadData(this.selectedDirectory);
    }

    loadData(path: string) {
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
                    this.collections = res;
                    this.collections.unshift(backElement);
                    console.log(this.collections);
                },
                error => {
                    console.log(error.status);
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
                console.log(data);
            }, error => {
                console.log(error);
                }
            );
        location.reload();
    }

    deleteColl(res) {
        this.fileExplorerService.deleteCollection(res)
            .subscribe(data => {
                    console.log(data);
                }, error => {
                    console.log(error);
                }
            );
        location.reload();
    }

    editCredentials(selectedRsourceCredentials) {
        this.fileExplorerService.setEditedFileCredentials(selectedRsourceCredentials);
        this.router.navigateByUrl('/file-credentials');
    }

    createFileHere(currentDir: string) {
        this.fileExplorerService.setSaveContentHere(currentDir);
        this.router.navigateByUrl('/create-file');
    }

    createDir(currentDir: string) {
        this.fileExplorerService.setSaveContentHere(currentDir);
        const dialogDirNameConfig = new MatDialogConfig();
        dialogDirNameConfig.disableClose = true;
        dialogDirNameConfig.autoFocus = true;
        dialogDirNameConfig.width = '40%';
        this.dialog.open(CreateDirDialogComponent, dialogDirNameConfig);
    }

    view(resCred: Credentials) {
        this.fileExplorerService.openedFile = resCred;
        const dialogDirNameConfig = new MatDialogConfig();
        dialogDirNameConfig.disableClose = true;
        dialogDirNameConfig.autoFocus = true;
        dialogDirNameConfig.width = '60%';
        this.dialog.open(FileViewerDialogComponent, dialogDirNameConfig);
    }

}
