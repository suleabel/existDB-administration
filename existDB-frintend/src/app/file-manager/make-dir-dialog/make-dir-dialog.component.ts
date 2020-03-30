import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-make-dir-dialog',
  templateUrl: './make-dir-dialog.component.html',
  styleUrls: ['./make-dir-dialog.component.sass']
})
export class MakeDirDialogComponent implements OnInit {
  public dirName = '';

  constructor(public dialogRef: MatDialogRef<MakeDirDialogComponent>) { }

  ngOnInit() {
  }

  onClose() {
    this.dialogRef.close();
  }

}
