import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SaveModel} from '../../xml-to-xsd/model/SaveXSDModel';
import {Credentials} from '../model/Credentials';
import {stringify} from 'querystring';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class FileExplorerService {
    private baseUrl = 'http://localhost:8085/existCollection/';
    private saveContentHere: string;
    private selectedResourceCredentials: Credentials;

    constructor(private http: HttpClient) {

    }

    public getCollection(collection: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getAllContentByCollection', collection);
    }

    public getBinResContent(resUrl: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getBinResContent', resUrl, {responseType: 'text'});
    }

    public saveResource(xsdString: SaveModel): Observable<any> {
        return this.http.post(this.baseUrl + 'store', xsdString, {responseType: 'text'});
    }

    public deleteResource(resUrl: Credentials): Observable<any> {
        return this.http.post(this.baseUrl + 'deleteRes', resUrl, httpOptions);
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
}
