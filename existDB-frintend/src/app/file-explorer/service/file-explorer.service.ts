import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class FileExplorerService {
  private baseUrl = 'http://localhost:8085/existCollection/';
  constructor(private http: HttpClient) {

  }
    public getCollection(collection: string): Observable<any> {
    return this.http.post(this.baseUrl + 'getAllContentByCollection', collection);
    }
}
