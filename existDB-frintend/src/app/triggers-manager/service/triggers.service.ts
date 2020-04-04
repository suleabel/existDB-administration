import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {EditTriggerModel} from '../model/EditTriggerModel';
import {StoreResourceModel} from "../../collection-manager/model/StoreResourceModel";

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class TriggersService {
    private SelectedCollection: string;
    /* tslint:disable:no-string-literal */
    private baseUrl = window['cfgApiBaseUrl'] + '/triggers/';
    /* tslint:enable:no-string-literal */
    constructor(private http: HttpClient) {
    }

    public getTriggerConfigs(collection: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getTriggersConfig', collection, httpOptions);
    }

    public addTrigger(data: EditTriggerModel): Observable<any> {
        return this.http.post(this.baseUrl + 'addTrigger', data, httpOptions);
    }

    public editTrigger(res: StoreResourceModel): Observable<any> {
        return this.http.post(this.baseUrl + 'editTrigger', res, httpOptions);
    }

    public initializeTriggerConfig(selectedCollection: string): Observable<any> {
        return this.http.post(this.baseUrl + 'initTriggerConfig', selectedCollection, httpOptions);
    }
}
