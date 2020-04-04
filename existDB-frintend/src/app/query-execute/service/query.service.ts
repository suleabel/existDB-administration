import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class QueryService {
    /* tslint:disable:no-string-literal */
    private baseUrl = window['cfgApiBaseUrl'] + '/query/';

    /* tslint:enable:no-string-literal */

    constructor(private http: HttpClient) {
    }

    public evalXqueryFromPath(url: string): Observable<any> {
        return this.http.post(this.baseUrl + 'evalXqueryasPath', url, httpOptions);
    }

    public evalXqueryFromString(query: string): Observable<any> {
        return this.http.post(this.baseUrl + 'evalXqueryasString', query, httpOptions);
    }
}
