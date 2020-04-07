import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ExistGroupModel} from '../model/existGroup.model';
import {stringify} from 'querystring';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class GroupsService {
    private selectedGroup: ExistGroupModel = null;
    /* tslint:disable:no-string-literal */
    private baseURL = window['cfgApiBaseUrl'] + '/groups/';
    private baseUrlforUsers = window['cfgApiBaseUrl'] + '/userManager/';
    /* tslint:enable:no-string-literal */
    constructor(private http: HttpClient) {
    }

    public getExistGroups(): Observable<any> {
        return this.http.get(this.baseURL + 'getGroups', httpOptions);
    }

    public addGroupToExist(groupModel: ExistGroupModel): Observable<any> {
        return this.http.post(this.baseURL + 'createGroup', groupModel, httpOptions);
    }

    public deleteGroup(group: string): Observable<any> {
        return this.http.post(this.baseURL + 'deleteGroup', group, httpOptions);
    }

    public getUsersNames(): Observable<any> {
        return this.http.get(this.baseUrlforUsers + 'getUsersNames', httpOptions);
    }

    public editGroups(group: ExistGroupModel): Observable<any> {
        return this.http.post(this.baseURL + 'editGroup', group, httpOptions);
    }

    public getSelectedGroup() {
        return this.selectedGroup;
    }

    public setSelectedGroup(group) {
        this.selectedGroup = group;
    }
}
