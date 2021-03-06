import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {Credentials} from '../../collection-manager/model/Credentials';
import {FileExplorerService} from '../../collection-manager/service/file-explorer.service';

@Component({
  selector: 'app-browse-save-location',
  templateUrl: './browse-save-location.component.html',
  styleUrls: ['./browse-save-location.component.sass']
})
export class BrowseSaveLocationComponent implements OnInit {
  public selectedDirectory = '/db';
  public collections: Credentials[];
  public displayedColumns: string[] = ['name', 'resource'];

  constructor(
      private fileExplorerService: FileExplorerService,
      private dialogRef: MatDialogRef<BrowseSaveLocationComponent>,
      private notificationService: NotificationService) { }

  ngOnInit() {
    this.loadData(this.selectedDirectory);
  }

  loadData(path: string) {
    this.fileExplorerService.getOnlyCollections(path)
        .subscribe(
            res => {
              const backElement = {
                name: '..',
                path: '',
                owner: '',
                group: '',
                mode: '',
                date: '',
                mime: '',
                locked: '',
                resource: false,
                triggerConfigAvailable: false
              };
              this.collections = res;
              this.collections.unshift(backElement);
            },
            error => {
              this.notificationService.Error(error.error);
              this.backToRoot();
            });
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

  aClick(col: string) {
    if (col === '..') {
      this.back();
    } else {
      this.selectedDirectory = this.selectedDirectory + '/' + col;
      this.loadData(this.selectedDirectory);
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
