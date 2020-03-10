import { Component, OnInit } from '@angular/core';
import {FileExplorerService} from '../service/file-explorer.service';
import {MatDialogRef} from '@angular/material';
import {NotificationService} from '../../error-dialog/service/notification.service';

@Component({
  selector: 'app-create-dir-dialog',
  templateUrl: './create-dir-dialog.component.html',
  styleUrls: ['./create-dir-dialog.component.sass']
})
export class CreateDirDialogComponent implements OnInit {
  public dirName = '';

  constructor(public dialogRef: MatDialogRef<CreateDirDialogComponent>,
              private fileExplorerService: FileExplorerService,
              private notificationService: NotificationService) { }

  ngOnInit() {
  }

  onCreate() {
    this.fileExplorerService.createdCollectionName = this.dirName;
    if (this.dirName === '') {
      alert('Directory name is empty!!');
      this.dialogRef.close();
    }
    this.fileExplorerService.createDir()
        .subscribe(
            data => {
              this.notificationService.success('Success');
            },
            error => {
              this.notificationService.warn('Error: ' + error);
            }
        );
    this.dialogRef.close();
  }

  onClose() {
    this.fileExplorerService.setSaveContentHere('');
    this.dialogRef.close();
  }

}
