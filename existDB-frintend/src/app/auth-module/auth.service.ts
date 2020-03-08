import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {JwtResponse} from './jwt-response';
import {AuthLoginInfo} from './login-info';
import {TokenStorageService} from './token-storage.service';
import {Router} from '@angular/router';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  /* tslint:disable:no-string-literal */
  private loginUrl = window['cfgApiBaseUrl'] + '/api/auth/signin';
  /* tslint:enable:no-string-literal */
  constructor(
    private http: HttpClient,
    private token: TokenStorageService,
    private router: Router) {
  }

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  get isLoggedIn() {
    // ha a token létezik
    const token = this.token.getToken();
    if (token !== null) {
      this.loggedIn.next(true);
    }
    return this.loggedIn.asObservable();
  }

  public logout() {
    this.loggedIn.next(false);
    this.token.signOut();
    this.router.navigate(['/login']);
  }
}
