import {Component, OnInit} from '@angular/core';
import {FileExplorerService} from '../service/file-explorer.service';
import {Router} from '@angular/router';
import {SaveModel} from '../../xml-to-xsd/model/SaveXSDModel';

@Component({
    selector: 'app-create-file',
    templateUrl: './create-file.component.html',
    styleUrls: ['./create-file.component.sass']
})
export class CreateFileComponent implements OnInit {
    private selectedPath = '';
    private textValue = '';
    private fileName = '';
    private saveData: SaveModel;

    constructor(private fileExplorerService: FileExplorerService, private router: Router) {
    }

    ngOnInit() {
        this.selectedPath = this.fileExplorerService.getSaveContentHere();
        if (this.selectedPath === null || this.selectedPath === undefined) {
            this.router.navigateByUrl('/file-explorer');
        }
    }

    sendText(): void {
        this.saveData = {url: this.selectedPath, fileName: this.fileName, content: this.textValue};
        if (this.saveData.fileName === '') {
            alert('File name is empty!');
        }
        if (this.saveData.content === '') {
            alert('File content is empty');
        }
        this.fileExplorerService.saveResource(this.saveData)
            .subscribe(
                data => {
                console.log(data);
                this.router.navigateByUrl('/file-explorer');
            }, error => {
                console.log(error);
            });

    }

    back() {
        this.router.navigateByUrl('/file-explorer');
    }
}
