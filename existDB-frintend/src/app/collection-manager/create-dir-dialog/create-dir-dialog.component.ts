import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-create-dir-dialog',
  templateUrl: './create-dir-dialog.component.html',
  styleUrls: ['./create-dir-dialog.component.sass']
})
export class CreateDirDialogComponent implements OnInit {
  public dirName = '';

  constructor(public dialogRef: MatDialogRef<CreateDirDialogComponent>) { }

  ngOnInit() {
  }

  onClose() {
    this.dialogRef.close();
  }

}
