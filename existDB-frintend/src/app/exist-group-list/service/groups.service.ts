import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ExistUserModel} from '../../user-manager-module/model/existUser.model';
import {Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class GroupsService {
  private selectedExistUser: ExistUserModel = null;
  private baseURL = 'http://localhost:8085/exist/';

  constructor(private http: HttpClient) { }

  public getExistGroups(): Observable<any> {
    return this.http.get(this.baseURL + 'getGroups');
  }

}
