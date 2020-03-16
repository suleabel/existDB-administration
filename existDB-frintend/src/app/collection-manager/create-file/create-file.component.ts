import {Component, OnInit} from '@angular/core';
import {FileExplorerService} from '../service/file-explorer.service';
import {Router} from '@angular/router';
import {StoreResourceModel} from '../model/StoreResourceModel';
import {NotificationService} from '../../error-dialog/service/notification.service';

@Component({
    selector: 'app-create-file',
    templateUrl: './create-file.component.html',
    styleUrls: ['./create-file.component.sass']
})
export class CreateFileComponent implements OnInit {
    public selectedPath = '';
    public textValue = '';
    public fileName = '';
    public saveData: StoreResourceModel;

    constructor(private fileExplorerService: FileExplorerService,
                private router: Router,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.selectedPath = this.fileExplorerService.getSaveContentHere();
        if (this.selectedPath === null || this.selectedPath === undefined) {
            this.router.navigateByUrl('/collection-manager');
        }
    }

    sendText(): void {
        this.saveData = {url: this.selectedPath, fileName: this.fileName, content: this.textValue, isBinary: true};
        if (this.saveData.fileName === '') {
            alert('File name is empty!');
        }
        if (this.saveData.content === '') {
            alert('File editedContent is empty');
        }
        console.log(this.saveData);
        this.fileExplorerService.saveResource(this.saveData)
            .subscribe(
                data => {
                this.notificationService.success('Success:' + data);
                this.router.navigateByUrl('/collection-manager');
            }, error => {
                    this.notificationService.warn('Error: ' + error);
            });

    }

    back() {
        this.router.navigateByUrl('/collection-manager');
    }
}