import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

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

  constructor(private http: HttpClient) {}

  public getRootDirectory(): Observable<any> {
    return this.http.get(this.baseUrl + 'getRootDirectory', {responseType: 'text'});
  }

  public getDirectoryContent(url: string): Observable<any> {
    return this.http.post(this.baseUrl + 'getDirectoryContent', url, httpOptions);
  }

  public readFile(url: string): Observable<any> {
    return this.http.post(this.baseUrl + 'readFile', url, {responseType: 'text'});
  }
}