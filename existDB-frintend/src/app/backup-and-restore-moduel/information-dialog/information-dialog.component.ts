import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-information-dialog',
  templateUrl: './information-dialog.component.html',
  styleUrls: ['./information-dialog.component.sass']
})
export class InformationDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<InformationDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit() {
  }
  onClose() {
    this.dialogRef.close();
  }

}
