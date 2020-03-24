import {Component, OnInit} from '@angular/core';
import {BackupRestoreService} from './service/backup-restore.service';
import {NotificationService} from '../error-dialog/service/notification.service';
import {BehaviorSubject} from 'rxjs';
import {BackupEntity} from './model/BackupEntity';
import {CreateBackupEntity} from './model/CreateBackupEntity';
import {MatDialog} from '@angular/material';
import {InformationDialogComponent} from './information-dialog/information-dialog.component';

@Component({
    selector: 'app-backup-and-restore-module',
    templateUrl: './backup-and-restore-module.component.html',
    styleUrls: ['./backup-and-restore-module.component.sass']
})
export class BackupAndRestoreModuleComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public isLoading2$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public location = '/exist/data/export';
    public backups: BackupEntity;
    public displayedColumns: string[] = ['fileName', 'nrInSequence', 'date', 'incremental', 'previous', 'download', 'restore'];
    public entity: CreateBackupEntity = {isZip: false, isIncremental: false};

    constructor(
        private backupService: BackupRestoreService,
        private notificationService: NotificationService,
        private dialog: MatDialog,) {
    }

    ngOnInit() {
        this.loadBackups(this.location);
    }

    loadBackups(location: string) {
        this.isLoading$.next(true);
        this.backupService.getBackups(location)
            .subscribe(data => {
                    console.log(data);
                    this.backups = data;
                    this.notificationService.success('Success');
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.warn(error.error.message);
                });
    }

    setDefaultLocation() {
        this.location = '/exist/data/export';
    }

    setBackupsLocation() {
        this.loadBackups(this.location);
    }

    changeZip() {
        this.entity.isZip = !this.entity.isZip;
    }

    changeIncremental() {
        this.entity.isIncremental = !this.entity.isIncremental;
    }

    restore(element) {
        console.log(element);
    }

    createBackup() {
        this.isLoading2$.next(true);
        console.log(this.entity);
        this.backupService.createBackup(this.entity)
            .subscribe(data => {
                this.notificationService.success('Backup created');
                this.isLoading2$.next(false);
                const dialogRef = this.dialog.open(InformationDialogComponent, {
                    width: '50%',
                    height: '80%',
                    data: {res: data}
                });
                dialogRef.afterClosed().subscribe(result => {
                    this.loadBackups(this.location);
                });
            }, error => {
                this.notificationService.warn(error.error.message);
            });
    }

}
