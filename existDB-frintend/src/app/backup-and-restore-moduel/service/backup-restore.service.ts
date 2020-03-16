import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreateBackupEntity} from '../model/CreateBackupEntity';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class BackupRestoreService {
  /* tslint:disable:no-string-literal */
  private baseUrl = window['cfgApiBaseUrl'] + '/backups/';
  /* tslint:enable:no-string-literal */

  constructor(private http: HttpClient) { }

  public getBackups(url: string): Observable<any> {
    return this.http.post(this.baseUrl + 'getBackups', url, httpOptions);
  }

  public createBackup(entity: CreateBackupEntity): Observable<any> {
    console.log(entity);
    return this.http.post(this.baseUrl + 'createBackup', entity, {responseType: 'text'});
  }


}
