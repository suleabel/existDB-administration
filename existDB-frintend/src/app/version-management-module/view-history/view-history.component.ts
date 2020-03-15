import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Credentials} from '../../collection-manager/model/Credentials';

@Component({
  selector: 'app-view-history',
  templateUrl: './view-history.component.html',
  styleUrls: ['./view-history.component.sass']
})
export class ViewHistoryComponent implements OnInit {
  private ok: boolean;
  private selectedResHistory;

  constructor(public dialogRef: MatDialogRef<ViewHistoryComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Credentials) { }

  ngOnInit() {
    this.ok = true;
    console.log(this.data);
  }

  onClose() {
    this.dialogRef.close();
  }

}
