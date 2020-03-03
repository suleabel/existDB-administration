import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../service/file-explorer.service';

@Component({
  selector: 'app-file-viewer-dialog',
  templateUrl: './file-viewer-dialog.component.html',
  styleUrls: ['./file-viewer-dialog.component.sass']
})
export class FileViewerDialogComponent implements OnInit {
  private viewResult: string;
  private openedFile: string;

  constructor(public dialogRef: MatDialogRef<FileViewerDialogComponent>,
              private fileExplorerService: FileExplorerService) { }

  ngOnInit() {
    this.openedFile = this.fileExplorerService.openedFile;
    this.fileExplorerService.getBinResContent(this.fileExplorerService.openedFile)
        .subscribe(
            data => {
              this.viewResult = data;
            },
            error => {
              console.log(error);
            }
        );
  }

  onClose() {
    this.fileExplorerService.setSaveContentHere('');
    this.dialogRef.close();
  }

}
