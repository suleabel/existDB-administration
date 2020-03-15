import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StoreResourceModel} from '../../collection-manager/model/StoreResourceModel';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class XmlToXsdService {
    /* tslint:disable:no-string-literal */
    private baseURL = window['cfgApiBaseUrl'] + '/xml2xsd/';
    /* tslint:enable:no-string-literal */

constructor(private http: HttpClient) { }

public sendXml(xmlString: string): Observable<any> {
    return this.http.post(this.baseURL + 'createXsd', xmlString, {responseType: 'text'});
}

public saveXsd(xsdString: StoreResourceModel): Observable<any> {
    return this.http.post(this.baseURL + 'saveXsd', xsdString, {responseType: 'text'});
}

}
