import { Injectable } from '@angular/core';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material';
import {DialogService} from './dialog.service';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(public snackBar: MatSnackBar,
              private dialogService: DialogService) { }

  config: MatSnackBarConfig = {
    duration: 6000,
    horizontalPosition: 'right',
    verticalPosition: 'top'
  };


  success(msg) {
    this.config.panelClass = ['notification', 'success'];
    this.snackBar.open(msg, '', this.config);
  }

  warn(msg) {
    this.config.panelClass = ['notification', 'warn'];
    this.snackBar.open('Error: ' + msg, '', this.config);
    this.dialogService.openErrorDialog(msg);
  }
}
