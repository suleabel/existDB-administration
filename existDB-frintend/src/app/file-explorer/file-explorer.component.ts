import {Component, OnInit} from '@angular/core';
import {FileExplorerService} from './service/file-explorer.service';
import {Router} from '@angular/router';
import {Credentials} from './model/Credentials';

@Component({
    selector: 'app-file-explorer',
    templateUrl: './file-explorer.component.html',
    styleUrls: ['./file-explorer.component.sass']
})
export class FileExplorerComponent implements OnInit {
    public selectedDirectory = '/db';
    public viewResult: string;
    public openedFile: string;
    public fileIsOpen = false;
    public collections: Credentials[];
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date', 'writable', 'delete', 'view',
        'editCredentials'];

    constructor(private fileExplorerService: FileExplorerService, private router: Router) {
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
                        resource: false
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
        const separatedString: string[] = this.selectedDirectory.split('/');
        separatedString.length = separatedString.length - 1;
        this.selectedDirectory = separatedString.join('/');
        this.loadData(this.selectedDirectory);
    }

    backToRoot() {
        this.selectedDirectory = '/db';
        this.fileIsOpen = false;
        this.loadData(this.selectedDirectory);
    }

    delete(res) {
        console.log(res);
        this.fileExplorerService.deleteResource(res)
            .subscribe(data => {
                console.log(data);
            }, error => {
                console.log(error);
                }
            );
    }

    editCredentials(selectedRsourceCredentials) {
        this.fileExplorerService.setEditedFileCredentials(selectedRsourceCredentials);
        this.router.navigateByUrl('/file-credentials');
    }

    createFileHere(currentDir: string) {
        this.fileExplorerService.setSaveContentHere(currentDir);
        this.router.navigateByUrl('/create-file');
    }

    view(resName: string) {
        this.openedFile = this.selectedDirectory + '/' + resName;
        this.fileIsOpen = true;
        this.fileExplorerService.getBinResContent(this.selectedDirectory + '/' + resName)
            .subscribe(
                data => {
                    this.viewResult = data;
                },
                error => {
                    console.log(error);
                }
            );
        console.log('selectedDor: ' + this.selectedDirectory + ' selected resource: ' + resName);
    }

}
