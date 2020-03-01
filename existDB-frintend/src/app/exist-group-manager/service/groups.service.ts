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
    private baseURL = 'http://localhost:8085/exist/';

    constructor(private http: HttpClient) {
    }

    public getExistGroups(): Observable<any> {
        return this.http.get(this.baseURL + 'getGroups');
    }

    public addGroupToExist(groupModel: ExistGroupModel): Observable<any> {
        console.log(stringify(groupModel));
        return this.http.post(this.baseURL + 'createGroup', groupModel, {responseType: 'text'});
    }

    public deleteGroup(group: string): Observable<any> {
        return this.http.post(this.baseURL + 'deleteGroup', group, {responseType: 'text'});
    }

    public getUsersNames(): Observable<any> {
        return this.http.get(this.baseURL + 'getUsersNames');
    }

    public editGroups(group: ExistGroupModel): Observable<any> {
        return this.http.post(this.baseURL + 'editGroup', group, {responseType: 'text'});
    }

    public getSelectedGroup() {
        return this.selectedGroup;
    }

    public setSelectedGroup(group) {
        this.selectedGroup = group;
    }
}
