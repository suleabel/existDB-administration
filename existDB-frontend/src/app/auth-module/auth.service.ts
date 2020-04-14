import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {JwtResponse} from './jwt-response';
import {AuthLoginInfo} from './login-info';
import {TokenStorageService} from './token-storage.service';
import {Router} from '@angular/router';
import {NotificationService} from '../error-notification-module/service/notification.service';

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
    private miscUrl = window['cfgApiBaseUrl'] + '/misc';
    /* tslint:enable:no-string-literal */
    private Url: string;
    private User: string;
    private ServerIp$: BehaviorSubject<string> = new BehaviorSubject<string>('');
    private ServerVersion$: BehaviorSubject<string> = new BehaviorSubject<string>('');

    constructor(
        private http: HttpClient,
        private token: TokenStorageService,
        private router: Router,
        private notificationService: NotificationService) {
    }

    public attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
        this.Url = credentials.url;
        this.User = credentials.username;
        return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
    }

    get isLoggedIn() {
        const token = TokenStorageService.getToken();
        if (token !== null) {
            this.loggedIn.next(true);
        }
        return this.loggedIn;
    }

    public setLogout() {
        this.loggedIn.next(false);
    }

    public logout() {
        this.loggedIn.next(false);
        this.token.signOut();
        this.router.navigate(['/login']);
    }

    public getDBVersion(): Observable<any> {
        return this.http.get(this.miscUrl + '/getVersion', httpOptions);
    }

    public setDBVersion() {
        this.getDBVersion().subscribe(
            data => {
                this.ServerVersion$.next(data.response);
            }, error => {
                this.notificationService.Error(error.error);
            }
        );
    }

    public getServerIp(): Observable<any> {
        return this.http.get(this.miscUrl + '/getServerIp', httpOptions);
    }

    public setServerIp() {
        this.getServerIp().subscribe(
            data => {
                this.ServerIp$.next(data.response.toLowerCase());
            }, error => {
                this.notificationService.Error(error.error);
            }
        );
    }


    get url(): string {
        return this.Url;
    }

    get user(): string {
        return this.User;
    }

    get serverIp(): BehaviorSubject<string>  {
        return this.ServerIp$;
    }

    get serverVersion(): BehaviorSubject<string> {
        return this.ServerVersion$;
    }
}
