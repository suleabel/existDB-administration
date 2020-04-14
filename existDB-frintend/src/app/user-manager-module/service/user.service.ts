import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ExistUserModel} from '../model/existUser.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private selectedExistUser: ExistUserModel = null;
  /* tslint:disable:no-string-literal */
  private baseUrlForUsers = window['cfgApiBaseUrl'] + '/userManager/';
  private baseUrlForGroups = window['cfgApiBaseUrl'] + '/groupManager/';
  /* tslint:enable:no-string-literal */
  constructor(private http: HttpClient) {
  }

  public getExistUsers(): Observable<any> {
    return this.http.get(this.baseUrlForUsers + 'getUsers', httpOptions);
  }
  public getGroupsNames(): Observable<any> {
    return this.http.get(this.baseUrlForGroups + 'getGroupsNames', httpOptions);
  }
  public addUserToExist(user: ExistUserModel): Observable<any> {
    return this.http.post(this.baseUrlForUsers + 'createUser', user, httpOptions);
  }
  public editUser(user: ExistUserModel): Observable<any> {
    return this.http.post(this.baseUrlForUsers + 'editUser', user, httpOptions);
  }
  public deleteUser(username: string): Observable<any> {
    return this.http.post(this.baseUrlForUsers + 'deleteUser', username, httpOptions);
  }
  public getSelectedExistUser() {
    return this.selectedExistUser;
  }
  public setSelectedExistUser(user) {
    this.selectedExistUser = user;
  }
}
