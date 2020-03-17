import {Component, OnInit} from '@angular/core';
import {BackupRestoreService} from './service/backup-restore.service';
import {NotificationService} from '../error-dialog/service/notification.service';
import {BehaviorSubject} from 'rxjs';
import {BackupEntity} from './model/BackupEntity';
import {CreateBackupEntity} from './model/CreateBackupEntity';

@Component({
    selector: 'app-backup-and-restore-module',
    templateUrl: './backup-and-restore-module.component.html',
    styleUrls: ['./backup-and-restore-module.component.sass']
})
export class BackupAndRestoreModuleComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public location = '/exist/data/export';
    public backups: BackupEntity;
    public displayedColumns: string[] = ['fileName', 'nrInSequence', 'date', 'incremental', 'previous', 'download'];
    public entity: CreateBackupEntity = {isZip: false, isIncremental: false};

    constructor(
        private backupService: BackupRestoreService,
        private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.loadBackups(this.location);
    }

    private loadBackups(location: string) {
        this.isLoading$.next(true);
        this.backupService.getBackups(location)
            .subscribe(data => {
                    console.log(data);
                    this.backups = data;
                    this.notificationService.success('Success');
                    this.isLoading$.next(false);
                },
                error => {
                    this.notificationService.warn('Error: ' + error.message);
                });
    }

    private setDefaultLocation() {
        this.location = '/exist/data/export';
    }

    private setBackupsLocation() {
        this.loadBackups(this.location);
    }

    private changeZip() {
        this.entity.isZip = !this.entity.isZip;
    }

    private changeIncremental() {
        this.entity.isIncremental = !this.entity.isIncremental;
    }

    private createBackup() {
        this.isLoading$.next(true);
        console.log(this.entity);
        this.backupService.createBackup(this.entity)
            .subscribe(data => {
                this.notificationService.success('Backup created');
                this.loadBackups(this.location);
            }, error => {
                this.notificationService.warn('Error: ' + error.message);
            });
    }

}
