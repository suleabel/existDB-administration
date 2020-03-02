import { Injectable } from '@angular/core';
import {MatDialog} from '@angular/material';
import {CollectionsDialogComponent} from '../collections-dialog/collections-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog: MatDialog) { }

  openConfirmDialog(msg) {
    return this.dialog.open(CollectionsDialogComponent,{
      width: '390px',
      panelClass: 'confirm-dialog-container',
      disableClose: true,
      position: { top: '10px' },
      data : {
        message : msg
      }
    });
  }
}
