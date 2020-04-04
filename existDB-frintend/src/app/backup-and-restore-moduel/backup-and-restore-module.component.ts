import {Component, OnInit} from '@angular/core';
import {BackupRestoreService} from './service/backup-restore.service';
import {BehaviorSubject} from 'rxjs';
import {BackupEntity} from './model/BackupEntity';
import {CreateBackupEntity} from './model/CreateBackupEntity';
import {MatDialog} from '@angular/material';
import {InformationDialogComponent} from './information-dialog/information-dialog.component';
import {Router} from '@angular/router';
import {NotificationService} from '../error-notification-module/service/notification.service';

@Component({
    selector: 'app-backup-and-restore-module',
    templateUrl: './backup-and-restore-module.component.html',
    styleUrls: ['./backup-and-restore-module.component.sass']
})
export class BackupAndRestoreModuleComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public isLoading2$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public rootLocation = '/exist/data/export';
    public backups: BackupEntity;
    public displayedColumns: string[] = ['fileName', 'nrInSequence', 'date', 'incremental', 'previous', 'restore'];
    public entity: CreateBackupEntity = {saveLocation: '/exist/data/export', isZip: 'false', isIncremental: 'false'};
    // public restoreURL = '/exist/apps/dashboard/bower_components/existdb-backup/modules/backup.xql?action=retrieve&archive=';

    constructor(
        private backupService: BackupRestoreService,
        private notificationService: NotificationService,
        private dialog: MatDialog,
        private router: Router) {
    }

    ngOnInit() {
        // console.log('http://' + this.backupService.getExistDbServerIp.toLowerCase() + this.restoreURL);
        this.loadBackups(this.rootLocation);
    }

    loadBackups(location: string) {
        this.isLoading$.next(true);
        this.backupService.getBackups(this.rootLocation)
            .subscribe(data => {
                    console.log(data);
                    this.backups = data;
                    this.notificationService.success('Success');
                    this.isLoading$.next(false);
                },
                error => {
                    console.log(error.error.message);
                    this.notificationService.Error(error.error);
                    // if (error-page.error-page.message.toLowerCase() === 'exerr:ERROR err:XPST0081 Invalid qname backups:list'.toLowerCase()) {
                    //     this.notificationService.Error2('Install backup module in package manager!!');
                    //     this.router.navigate(['home']);
                    // } else {
                    //     this.notificationService.Error(error-page.error-page);
                    // }
                });
    }

    setDefaultLocation() {
        this.rootLocation = '/exist/data/export';
    }

    setBackupsLocation() {
        this.loadBackups(this.rootLocation);
    }

    changeZip() {
        if (this.entity.isZip === 'true') {
           this.entity.isZip = 'false';
        } else {
            this.entity.isZip = 'true';
        }
    }

    changeIncremental() {
        if (this.entity.isIncremental === 'true') {
            this.entity.isIncremental = 'false';
        } else {
            this.entity.isIncremental = 'true';
        }
    }

    restore(element) {
        this.isLoading2$.next(true);
        console.log(element.fileName);
        const backupPath = this.rootLocation + '/' + element.fileName;
        this.backupService.restoreBackup(backupPath)
            .subscribe(data => {
                this.notificationService.success('Database is restored');
                this.isLoading2$.next(false);
                const dialogRef = this.dialog.open(InformationDialogComponent, {
                    width: '50%',
                    height: 'auto',
                    maxHeight: '80%',
                    data: {res: data.response}
                });
                dialogRef.afterClosed().subscribe(result => {
                    this.loadBackups(this.rootLocation);
                });
            }, error => {
                console.log(error.error);
                this.notificationService.Error(error.error);
                this.isLoading2$.next(false);
            });
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
                    data: {res: data.response}
                });
                dialogRef.afterClosed().subscribe(result => {
                    this.loadBackups(this.rootLocation);
                });
            }, error => {
                this.notificationService.Error(error.error);
                this.isLoading2$.next(false);
            });
    }

}
