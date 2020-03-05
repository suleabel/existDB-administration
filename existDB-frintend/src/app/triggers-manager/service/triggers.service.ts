import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {EditTriggerModel} from '../model/EditTriggerModel';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class TriggersService {
    private SelectedCollection: string;
    private baseUrl = 'http://localhost:8085/triggers/';

    constructor(private http: HttpClient) {
    }

    public getTriggerConfigs(collection: string): Observable<any> {
        return this.http.post(this.baseUrl + 'getTriggersConfig', collection, httpOptions);
    }

    public addTrigger(data: EditTriggerModel): Observable<any> {
        console.log(data);
        return this.http.post(this.baseUrl + 'editTrigger', data, {responseType: 'text'});
    }

    public initializeTriggerConfig(): Observable<any> {
        return this.http.post(this.baseUrl + 'initTriggerConfig', this.selectedCollection, {responseType: 'text'});
    }


    get selectedCollection(): string {
        return this.SelectedCollection;
    }

    set selectedCollection(value: string) {
        this.SelectedCollection = value;
    }
}
