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

  public getAllUser(): Observable<any> {
    return this.http.get(this.baseUrl + 'getAllUser');
  }

  // exist
  public getExistUsers(): Observable<any> {
    return this.http.get(this.baseUrlForExist + 'getUsers');
  }

  public getGroupsNames(): Observable<any> {
    return this.http.get(this.baseUrlForExist + 'getGroupsNames');
  }

  public getUserById(id): Observable<any> {
    return this.http.get<UsersListModel>(this.baseUrl + 'getUserById/?id=' + id);
  }

  public saveUser(user: UsersListModel): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'saveUser', user, httpOptions);
  }

  // exist
  public addUserToExist(user: ExistUserModel): Observable<any> {
    return this.http.post<any>(this.baseUrlForExist + 'createUser', user, httpOptions);
  }

  // exist
  public editUser(user: ExistUserModel): Observable<any> {
    return this.http.post<any>(this.baseUrlForExist + 'editUser', user, httpOptions);
  }

  // exist
  public deleteUser(username: string): Observable<any> {
    return this.http.post<any>(this.baseUrlForExist + 'deleteUser', username, httpOptions);
  }

  // exist
  public getSelectedExistUser() {
    return this.selectedExistUser;
  }

  // exist
  public setSelectedExistUser(user) {
    this.selectedExistUser = user;
  }

  public getSelectedUser() {
    return this.selectedUser;
  }

  public setSelectedUser(user) {
    this.selectedUser = user;
  }
}
