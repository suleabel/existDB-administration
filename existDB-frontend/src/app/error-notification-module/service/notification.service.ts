import {Injectable} from '@angular/core';
import {DialogService} from './dialog.service';
import {ErrorModel} from '../model/ErrorModel';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root'
})
export class NotificationService {
    private error: ErrorModel;

    constructor(public snackBar: MatSnackBar,
                private dialogService: DialogService) {
    }

    config: MatSnackBarConfig = {
        duration: 3000,
        horizontalPosition: 'right',
        verticalPosition: 'bottom'
    };


    success(msg) {
        this.config.panelClass = ['notification', 'success'];
        this.snackBar.open(msg, '', this.config);
    }

    Error(msg: ErrorModel) {
        this.error = msg;
        this.config.panelClass = ['notification', 'warn'];
        this.snackBar.open('Error: ' + this.error.message, '', this.config);
        this.dialogService.openErrorDialog(this.error.message);
    }

    Error2(msg) {
        this.error = msg;
        this.config.panelClass = ['notification', 'warn'];
        this.snackBar.open('Error: ' + this.error, '', this.config);
        this.dialogService.openErrorDialog(this.error);
    }

    result(result) {
        this.dialogService.openResponseDialog(result);
    }

    warn(msg) {
        this.config.panelClass = ['notification', 'warn'];
        this.snackBar.open('Error: ' + msg, '', this.config);
    }
}
