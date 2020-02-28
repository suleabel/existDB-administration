import {Component, Inject, OnInit} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

export interface DialogData {
  animal: string;
  name: string;
}

@Component({
  selector: 'app-dialog-panel',
  templateUrl: './dialog-panel.component.html',
  styleUrls: ['./dialog-panel.component.sass']
})
export class DialogPanelComponent implements OnInit {

  constructor(
      public dialogRef: MatDialogRef<DialogPanelComponent>,
      @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
