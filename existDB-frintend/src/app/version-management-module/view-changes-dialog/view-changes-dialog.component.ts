import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-view-changes-dialog',
  templateUrl: './view-changes-dialog.component.html',
  styleUrls: ['./view-changes-dialog.component.sass']
})
export class ViewChangesDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ViewChangesDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit() {
  }

  onClose() {
    this.dialogRef.close();
  }
}
