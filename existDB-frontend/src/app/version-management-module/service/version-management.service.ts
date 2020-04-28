import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GetDiffByRevModel} from '../model/GetDiffByRevModel';

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
        return this.http.get(this.baseUrlForExist + 'enableVersionManagement', httpOptions);
    }

    public versionManagerIsActivated(): Observable<any> {
        return this.http.get(this.baseUrlForExist + 'isEnabled', httpOptions);
    }

    public getResHistory(path: string): Observable<any> {
        return this.http.post(this.baseUrlForExist + 'getResHistory', path, httpOptions);
    }

    public getDiffByRev(data: GetDiffByRevModel): Observable<any> {
         return this.http.post(this.baseUrlForExist + 'getDiffByRev', data, {responseType: 'text'});
    }

    public restoreResByRev(data: GetDiffByRevModel): Observable<any> {
        return this.http.post(this.baseUrlForExist + 'restoreResByRev', data, {responseType: 'text'});
    }

}
