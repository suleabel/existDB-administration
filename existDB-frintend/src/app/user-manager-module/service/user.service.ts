import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UsersListModel} from '../model/users-list.model';
import {Observable} from 'rxjs';
import {ExistUserModel} from '../model/existUser.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private selectedUser: UsersListModel = null;
  private selectedExistUser: ExistUserModel = null;
  private baseUrl = 'http://localhost:8085/userManager/';
  private baseUrlForExist = 'http://localhost:8085/exist/';

  constructor(private http: HttpClient) {
  }

  // exist
  public getExistUsers(): Observable<any> {
    return this.http.get(this.baseUrlForExist + 'getUsers');
  }

  // exist
  public getGroupsNames(): Observable<any> {
    return this.http.get(this.baseUrlForExist + 'getGroupsNames');
  }

  // exist
  public addUserToExist(user: ExistUserModel): Observable<any> {
    return this.http.post(this.baseUrlForExist + 'createUser', user, {responseType: 'text'});
  }

  // exist
  public editUser(user: ExistUserModel): Observable<any> {
    return this.http.post(this.baseUrlForExist + 'editUser', user, {responseType: 'text'});
  }

  // exist
  public deleteUser(username: string): Observable<any> {
    return this.http.post(this.baseUrlForExist + 'deleteUser', username, {responseType: 'text'});
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
