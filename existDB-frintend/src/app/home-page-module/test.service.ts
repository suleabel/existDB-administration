import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class TestService {
    /* tslint:disable:no-string-literal */
    private baseUrl = window['cfgApiBaseUrl'] + '/test/';

    /* tslint:enable:no-string-literal */

    constructor(private http: HttpClient) {
    }

    public test(): Observable<any> {
        return this.http.get(this.baseUrl + 'test', httpOptions);
    }
}
