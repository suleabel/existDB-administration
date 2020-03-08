import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StoreResourceModel} from '../model/StoreResourceModel';
import {Credentials} from '../model/Credentials';
import {stringify} from 'querystring';

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
    private saveContentHere: string;
    private CreatedCollectionName: string;
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
        return this.http.post(this.baseUrl + 'store', res, {responseType: 'text'});
    }

    public editResource(res: StoreResourceModel): Observable<any> {
        return this.http.post(this.baseUrl + 'saveEdit', res, {responseType: 'text'});
    }

    public createDir(): Observable<any> {
        const col: StoreResourceModel = {url: this.getSaveContentHere(), fileName: this.createdCollectionName, content: null, isBinary: false};
        return this.http.post(this.baseUrl + 'createDir', col, {responseType: 'text'});
    }

    public deleteResource(cred: Credentials): Observable<any> {
        return this.http.post(this.baseUrl + 'deleteRes', cred, {responseType: 'text'});
    }

    public deleteCollection(cred: Credentials): Observable<any> {
        console.log(stringify(cred));
        return this.http.post(this.baseUrl + 'deleteColl', cred, {responseType: 'text'});
    }

    public editFileCredentials(cred: Credentials): Observable<any> {
        console.log(cred);
        return this.http.post(this.baseUrl + 'editResCred', cred, {responseType: 'text'});
    }





    public setSaveContentHere(url: string) {
        this.saveContentHere = url;
        console.log('createFileHere: ' + this.saveContentHere);
    }

    public getSaveContentHere(): string {
        return this.saveContentHere;
    }

    public setEditedFileCredentials(url: Credentials) {
        this.selectedResourceCredentials = url;
        console.log('createFileHere: ' + this.saveContentHere);
    }

    public getEditedFileCredentials(): Credentials {
        return this.selectedResourceCredentials;
    }


    get createdCollectionName(): string {
        return this.CreatedCollectionName;
    }

    set createdCollectionName(value: string) {
        this.CreatedCollectionName = value;
    }


    get openedFile(): Credentials {
        return this.OpenedFile;
    }

    set openedFile(value: Credentials) {
        this.OpenedFile = value;
    }
}
