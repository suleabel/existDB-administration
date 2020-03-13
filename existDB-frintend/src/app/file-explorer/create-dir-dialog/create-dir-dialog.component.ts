import { Component, OnInit } from '@angular/core';
import {FileExplorerService} from '../service/file-explorer.service';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-create-dir-dialog',
  templateUrl: './create-dir-dialog.component.html',
  styleUrls: ['./create-dir-dialog.component.sass']
})
export class CreateDirDialogComponent implements OnInit {
  public dirName = '';

  constructor(public dialogRef: MatDialogRef<CreateDirDialogComponent>,
              private fileExplorerService: FileExplorerService) { }

  ngOnInit() {
  }

  onClose() {
    this.fileExplorerService.setSaveContentHere('');
    this.dialogRef.close();
  }

}
