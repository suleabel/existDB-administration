import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FileManagerService} from '../service/file-manager.service';
import {NotificationService} from '../../error-dialog/service/notification.service';

@Component({
  selector: 'app-file-viewer-dialog',
  templateUrl: './file-viewer-dialog.component.html',
  styleUrls: ['./file-viewer-dialog.component.sass']
})
export class FileViewerDialogComponent implements OnInit {
  public fullPath: string;
  public content: string;

  constructor(private dialogRef: MatDialogRef<FileViewerDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data,
              private fileManagerService: FileManagerService,
              private notificationService: NotificationService) { }

  ngOnInit() {
    this.fullPath = this.data.url + '/' + this.data.fileData.name;
    console.log(this.fullPath);
    this.readFile(this.fullPath);
  }

  readFile(fullPath) {
    this.fileManagerService.readFile(fullPath)
        .subscribe(data => {
          this.content = data;
        }, error => {
          this.notificationService.Error(error.error);
        }
    );
  }

  onClose() {
    this.dialogRef.close();
  }

}
