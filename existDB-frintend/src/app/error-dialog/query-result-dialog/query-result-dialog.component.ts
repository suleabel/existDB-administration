import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-query-result-dialog',
  templateUrl: './query-result-dialog.component.html',
  styleUrls: ['./query-result-dialog.component.sass']
})
export class QueryResultDialogComponent implements OnInit {
  public result: string;

  constructor(@Inject(MAT_DIALOG_DATA) public data,
              public dialogRef: MatDialogRef<QueryResultDialogComponent>) { }

  ngOnInit() {
    this.result = this.data.res;
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
