import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreateBackupEntity} from '../model/CreateBackupEntity';
import {TokenStorageService} from '../../auth-module/token-storage.service';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class BackupRestoreService {
    /* tslint:disable:no-string-literal */
    private baseUrl = window['cfgApiBaseUrl'] + '/backups/';
    private existDbServerIp: string;

    /* tslint:enable:no-string-literal */

    constructor(private http: HttpClient,
                private tokenStorage: TokenStorageService) {
        this.existDbServerIp = this.tokenStorage.getServerIp();
    }

    public getBackups(url: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getBackups', url, httpOptions);
    }

    public createBackup(entity: CreateBackupEntity): Observable<any> {
        console.log(entity);
        return this.http.post(this.baseUrl + 'createBackup', entity, {responseType: 'text'});
    }

    public restoreBackup(path: string): Observable<any> {
        return this.http.post(this.baseUrl + 'restoreBackup', path, {responseType: 'text'});
    }

    get getExistDbServerIp(): string {
        return this.existDbServerIp;
    }
}
