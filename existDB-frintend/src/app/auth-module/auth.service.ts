import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {JwtResponse} from './jwt-response';
import {AuthLoginInfo} from './login-info';
import {SignUpInfo} from './signup-info';
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
  private userIsAdmin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private userIsPm: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  private loginUrl = 'http://localhost:8085/api/auth/signin';
  private signupUrl = 'http://localhost:8085/api/auth/signup';

  constructor(
    private http: HttpClient,
    private token: TokenStorageService,
    private router: Router) {
  }

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignUpInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

  get isLoggedIn() {

    // ha a token létezik
    const token = this.token.getToken();
    if (token !== null) {
      this.loggedIn.next(true);
    }

    // ha a user role-ja admin vagy pm
    this.token.getAuthorities().forEach((role) => {
      if (role === 'ROLE_ADMIN') {
        this.userIsAdmin.next(true);
      }
      if (role === 'ROLE_PM') {
        this.userIsPm.next(true);
      }
    });
    return this.loggedIn.asObservable();
  }

  public logout() {
    this.loggedIn.next(false);
    this.userIsAdmin.next(false);
    this.userIsPm.next(false);
    this.token.signOut();
    this.router.navigate(['/login']);
  }

  get isAdmin(): Observable<boolean> {
    return this.userIsAdmin;
  }

  get isPm(): Observable<boolean> {
    return this.userIsPm;
  }
}
