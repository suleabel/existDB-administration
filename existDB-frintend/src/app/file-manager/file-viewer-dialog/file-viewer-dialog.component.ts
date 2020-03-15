import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FileManagerService} from '../service/file-manager.service';

@Component({
  selector: 'app-file-viewer-dialog',
  templateUrl: './file-viewer-dialog.component.html',
  styleUrls: ['./file-viewer-dialog.component.sass']
})
export class FileViewerDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<FileViewerDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data,
              private fileManagerService: FileManagerService ) { }

  ngOnInit() {
    this.readFile();
  }

  readFile() {
    this.fileManagerService.readFile(this.data.url + '/' + this.data.fileData.name)
        .subscribe(data => {
          console.log(data);
        }, error => {
          console.log(error);
        }
    );
  }

  onClose() {
    this.dialogRef.close();
  }

}
