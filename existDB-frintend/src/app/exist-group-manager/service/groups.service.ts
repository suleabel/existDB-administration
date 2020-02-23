import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ExistGroupModel} from '../model/existGroup.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class GroupsService {
  private selectedGroup: ExistGroupModel = null;
  private baseURL = 'http://localhost:8085/exist/';

  constructor(private http: HttpClient) { }

  public getExistGroups(): Observable<any> {
    return this.http.get(this.baseURL + 'getGroups');
  }

  public addGroupToExist(user: ExistGroupModel): Observable<any> {
    return this.http.post<any>(this.baseURL + 'createGroup', user, httpOptions);
  }

  public deleteGroup(group: string): Observable<any> {
    return this.http.post<any>(this.baseURL + 'deleteGroup', group, httpOptions);
  }

  public getUsersNames(): Observable<any> {
    return this.http.get(this.baseURL + 'getUsersNames');
  }

  public getSelectedGroup() {
    return this.selectedGroup;
  }

  public setSelectedGroup(group) {
    this.selectedGroup = group;
  }
}
