import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class VersionManagementService {
  /* tslint:disable:no-string-literal */
  private baseUrlForExist = window['cfgApiBaseUrl'] + '/version/';
  /* tslint:enable:no-string-literal */

  constructor(private http: HttpClient) {
  }
    public enableVersionManager(): Observable<any> {
      return this.http.get(this.baseUrlForExist + 'enableVersionManagement', {responseType: 'text'});
    }
    public versionManagerIsActivated(): Observable<any> {
      return this.http.get(this.baseUrlForExist + 'isEnabled', {responseType: 'text'});
    }
}
