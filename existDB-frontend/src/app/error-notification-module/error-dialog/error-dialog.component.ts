import {Component, Inject, OnInit} from '@angular/core';
import {ErrorModel} from '../model/ErrorModel';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.component.html',
  styleUrls: ['./error-dialog.component.sass']
})
export class ErrorDialogComponent implements OnInit {
  public error: ErrorModel;

  constructor(@Inject(MAT_DIALOG_DATA) public data,
              public dialogRef: MatDialogRef<ErrorDialogComponent>) { }

  ngOnInit() {
    this.error = this.data.error;
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
