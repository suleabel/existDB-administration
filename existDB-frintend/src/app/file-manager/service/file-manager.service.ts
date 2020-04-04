import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StoreDirOrFileModel} from '../model/StoreDirOrFileModel';
import {SerializeFileModel} from '../model/SerializeFileModel';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class FileManagerService {
    /* tslint:disable:no-string-literal */
    private baseUrl = window['cfgApiBaseUrl'] + '/fileManager/';

    /* tslint:enable:no-string-literal */

    constructor(private http: HttpClient) {
    }

    public getRootDirectory(): Observable<any> {
        return this.http.get(this.baseUrl + 'getRootDirectory', httpOptions);
    }

    public getDirectoryContent(url: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getDirectoryContent', url, httpOptions);
    }

    public readFile(url: string): Observable<any> {
        return this.http.post(this.baseUrl + 'readFile', url, httpOptions);
    }

    public makeDir(data: StoreDirOrFileModel): Observable<any> {
        return this.http.post(this.baseUrl + 'makeDir', data, httpOptions);
    }

    public delete(data: StoreDirOrFileModel): Observable<any> {
        return this.http.post(this.baseUrl + 'delete', data, httpOptions);
    }

    public serializeFile(data: SerializeFileModel): Observable<any> {
        return this.http.post(this.baseUrl + 'serializeFile', data, httpOptions);
    }

    public editFile(data: SerializeFileModel): Observable<any> {
        return this.http.post(this.baseUrl + 'editFile', data, httpOptions);
    }
}
