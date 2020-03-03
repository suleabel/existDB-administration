import { Component, OnInit } from '@angular/core';
import {FileExplorerService} from '../service/file-explorer.service';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-create-dir-dialog',
  templateUrl: './create-dir-dialog.component.html',
  styleUrls: ['./create-dir-dialog.component.sass']
})
export class CreateDirDialogComponent implements OnInit {
  private dirName = '';

  constructor(public dialogRef: MatDialogRef<CreateDirDialogComponent>,
              private fileExplorerService: FileExplorerService) { }

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
              console.log(data);
            },
            error => {
              console.log(error);
            }
        );
    this.dialogRef.close();
  }

  onClose() {
    this.fileExplorerService.setSaveContentHere('');
    this.dialogRef.close();
  }

}
