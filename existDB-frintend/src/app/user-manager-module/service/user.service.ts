import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UsersListModel} from '../model/users-list.model';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private selectedUser: UsersListModel = null;
  private baseUrl = 'http://localhost:8080/userManager/';

  constructor(private http: HttpClient) {
  }

  public getAllUser(): Observable<any> {
    return this.http.get(this.baseUrl + 'getAllUser');
  }

  public getUserById(id): Observable<any> {
    return this.http.get<UsersListModel>(this.baseUrl + 'getUserById/?id=' + id);
  }

  public saveUser(user: UsersListModel): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'saveUser', user, httpOptions);
  }

  public getSelectedUser() {
    return this.selectedUser;
  }

  public setSelectedUser(user) {
    // console.log('userService -> setSelectedUser' + id);
    // this.getUserById(id)
    //   .subscribe(
    //     res => {
    //       this.selectedUser = res;
    //       console.log('selectedUser: ' + JSON.stringify(this.selectedUser));
    //     }
    //   );
    this.selectedUser = user;
  }
}
