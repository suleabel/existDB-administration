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
  private baseUrlForGroups = window['cfgApiBaseUrl'] + '/groups/';
  /* tslint:enable:no-string-literal */
  constructor(private http: HttpClient) {
  }

  // exist
  public getExistUsers(): Observable<any> {
    return this.http.get(this.baseUrlForUsers + 'getUsers', httpOptions);
  }

  // exist
  public getGroupsNames(): Observable<any> {
    return this.http.get(this.baseUrlForGroups + 'getGroupsNames', httpOptions);
  }

  // exist
  public addUserToExist(user: ExistUserModel): Observable<any> {
    return this.http.post(this.baseUrlForUsers + 'createUser', user, httpOptions);
  }

  // exist
  public editUser(user: ExistUserModel): Observable<any> {
    return this.http.post(this.baseUrlForUsers + 'editUser', user, httpOptions);
  }

  // exist
  public deleteUser(username: string): Observable<any> {
    return this.http.post(this.baseUrlForUsers + 'deleteUser', username, httpOptions);
  }

  // exist
  public getSelectedExistUser() {
    return this.selectedExistUser;
  }

  // exist
  public setSelectedExistUser(user) {
    this.selectedExistUser = user;
  }
}
