import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StoreResourceModel} from '../model/StoreResourceModel';
import {Credentials} from '../model/Credentials';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class FileExplorerService {
    /* tslint:disable:no-string-literal */
    private baseUrl = window['cfgApiBaseUrl'] + '/collection/';
    /* tslint:enable:no-string-literal */
    private selectedResourceCredentials: Credentials;
    private OpenedFile: Credentials;

    constructor(private http: HttpClient) {

    }

    public getCollection(collection: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getAllContentByCollection', collection);
    }

    public getOnlyCollections(collection: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getOnlyCollections', collection);
    }

    public getResContent(resUrl: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getFileContent', resUrl, httpOptions);
    }

    public saveResource(res: StoreResourceModel): Observable<any> {
        return this.http.post(this.baseUrl + 'store', res, httpOptions);
    }

    public editResource(res: StoreResourceModel): Observable<any> {
        return this.http.post(this.baseUrl + 'saveEdit', res, httpOptions);
    }

    public createDir(col: StoreResourceModel): Observable<any> {
        return this.http.post(this.baseUrl + 'createDir', col, httpOptions);
    }

    public deleteResource(cred: Credentials): Observable<any> {
        return this.http.post(this.baseUrl + 'deleteRes', cred, httpOptions);
    }

    public deleteCollection(cred: Credentials): Observable<any> {
        return this.http.post(this.baseUrl + 'deleteColl', cred, httpOptions);
    }

    public editResCredentials(cred: Credentials): Observable<any> {
        return this.http.post(this.baseUrl + 'editResCred', cred, httpOptions);
    }

    public evalXqueryFromPath(url: string): Observable<any> {
        return this.http.post(this.baseUrl + 'evalXqueryasPath', url, httpOptions);
    }

    public evalXqueryFromString(query: string): Observable<any> {
        return this.http.post(this.baseUrl + 'evalXqueryasString', query, httpOptions);
    }

    public unlockResource(url: string): Observable<any> {
        return this.http.post(this.baseUrl + 'unlockResource', url, httpOptions);
    }


    get openedFile(): Credentials {
        return this.OpenedFile;
    }

    set openedFile(value: Credentials) {
        this.OpenedFile = value;
    }
}
