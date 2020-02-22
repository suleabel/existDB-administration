import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AddExistGroupModel} from '../../user-manager-module/model/add-existGroup.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class GroupsService {
  private baseURL = 'http://localhost:8085/exist/';

  constructor(private http: HttpClient) { }

  public getExistGroups(): Observable<any> {
    return this.http.get(this.baseURL + 'getGroups');
  }

  public addGroupToExist(user: AddExistGroupModel): Observable<any> {
    return this.http.post<any>(this.baseURL + 'createGroup', user, httpOptions);
  }

  public deleteGroup(group: string): Observable<any> {
    return this.http.post<any>(this.baseURL + 'deleteGroup', group, httpOptions);
  }

  public getUsersNames(): Observable<any> {
    return this.http.get(this.baseURL + 'getUsersNames');
  }

}
