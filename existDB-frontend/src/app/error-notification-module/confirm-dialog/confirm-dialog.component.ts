import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-error-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.sass']
})
export class ConfirmDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data,
              public dialogRef: MatDialogRef<ConfirmDialogComponent>) { }

  ngOnInit() {
    console.log(this.data.message);
  }

  closeDialog() {
    this.dialogRef.close(false);
  }

}
