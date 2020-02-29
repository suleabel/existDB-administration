import {Component, OnInit} from '@angular/core';
import {FileExplorerService} from './service/file-explorer.service';
import {Router} from '@angular/router';

export interface Content {
    name: string;
    owner: string;
    groups: string;
    mode: string;
    date: string;
    writeable: boolean;
    isResource: boolean;
}

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
    public collections: Content[];
    public displayedColumns: string[] = ['name', 'resource', 'owner', 'group', 'mode', 'date', 'writeable', 'delete', 'view'];

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
                        owner: '',
                        groups: '',
                        mode: '',
                        date: '',
                        writeable: false,
                        isResource: false
                    };
                    this.collections = res;
                    this.collections.unshift(backElement);
                    // this.collections.push(backElement);
                    console.log(this.collections);
                },
                error => {
                    console.log(error);
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

    delete(resName: string) {
        console.log('selectedDor: ' + this.selectedDirectory + ' selected resource: ' + resName);
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
