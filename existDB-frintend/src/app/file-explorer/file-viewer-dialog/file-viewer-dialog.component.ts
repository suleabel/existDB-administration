import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../service/file-explorer.service';
import {Credentials} from '../model/Credentials';

@Component({
  selector: 'app-file-viewer-dialog',
  templateUrl: './file-viewer-dialog.component.html',
  styleUrls: ['./file-viewer-dialog.component.sass']
})
export class FileViewerDialogComponent implements OnInit {
  private viewResult: string;

  constructor(public dialogRef: MatDialogRef<FileViewerDialogComponent>,
              private fileExplorerService: FileExplorerService) { }

  ngOnInit() {
    if (this.fileExplorerService.openedFile.name.includes('.xml') || this.fileExplorerService.openedFile.name.includes('.xconf')) {
        this.fileExplorerService.getXmlResContent(this.fileExplorerService.openedFile.path + '/' + this.fileExplorerService.openedFile.name)
            .subscribe(
                data => {
                    this.viewResult = data;
                },
                error => {
                    console.log(error);
                }
            );
    } else {
        this.fileExplorerService.getBinResContent(this.fileExplorerService.openedFile.path + '/' + this.fileExplorerService.openedFile.name)
            .subscribe(
                data => {
                    this.viewResult = data;
                },
                error => {
                    console.log(error);
                }
            );
    }
  }

  onClose() {
    this.fileExplorerService.setSaveContentHere('');
    this.dialogRef.close();
  }

}
