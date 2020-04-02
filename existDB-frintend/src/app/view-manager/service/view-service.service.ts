import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CreateViewModel} from '../model/CreateViewModel';
import {Observable} from 'rxjs';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class ViewServiceService {
    /* tslint:disable:no-string-literal */
    private baseUrl = window['cfgApiBaseUrl'] + '/view/';

    /* tslint:enable:no-string-literal */
    constructor(private http: HttpClient) {
    }

    public createView(data: CreateViewModel): Observable<any> {
        return this.http.post(this.baseUrl + 'createView', data, httpOptions);
    }
}
