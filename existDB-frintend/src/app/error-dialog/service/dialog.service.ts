import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../confirm-dialog/confirm-dialog.component';
import {ErrorDialogComponent} from '../error-dialog/error-dialog.component';
import {QueryResultDialogComponent} from "../query-result-dialog/query-result-dialog.component";

@Injectable({
    providedIn: 'root'
})
export class DialogService {

    constructor(private dialog: MatDialog) {
    }

    openConfirmDialog(msg) {
        return this.dialog.open(ConfirmDialogComponent, {
            width: '390px',
            panelClass: 'confirm-dialog-container',
            disableClose: true,
            position: {top: '100px'},
            data: {
                message: msg
            }
        });
    }

    openErrorDialog(msg) {
        return this.dialog.open(ErrorDialogComponent, {
            width: 'auto',
            minWidth: '300px',
            panelClass: 'error-dialog-container',
            disableClose: false,
            data: {
                error: msg
            }
        });
    }

    openResponseDialog(result) {
        return this.dialog.open(QueryResultDialogComponent, {
            width: 'auto',
            minWidth: '300px',
            panelClass: 'error-dialog-container',
            disableClose: false,
            data: {
                res: result
            }
        });
    }
}
