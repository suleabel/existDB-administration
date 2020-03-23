import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-eval-result-viewer',
  templateUrl: './eval-result-viewer.component.html',
  styleUrls: ['./eval-result-viewer.component.sass']
})
export class EvalResultViewerComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EvalResultViewerComponent>,
              @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit() {
  }

  onClose() {
    this.dialogRef.close();
  }

}
