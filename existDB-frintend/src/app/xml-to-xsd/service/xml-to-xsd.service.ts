import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SaveModel} from '../model/SaveXSDModel';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class XmlToXsdService {
  private baseURL = 'http://localhost:8085/xml2xsd/';

  constructor(private http: HttpClient) { }

    public sendXml(xmlString: string): Observable<any> {
        return this.http.post(this.baseURL + 'createXsd', xmlString, {responseType: 'text'});
  }

    public saveXsd(xsdString: SaveModel): Observable<any> {
        return this.http.post(this.baseURL + 'saveXsd', xsdString, {responseType: 'text'});
    }

}
