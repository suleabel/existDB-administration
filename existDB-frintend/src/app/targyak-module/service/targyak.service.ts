import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TargyakService {

  private baseUrl = 'http://localhost:8085/rest/';

  constructor(private http: HttpClient) {
  }

  // tslint:disable-next-line:ban-types
  public getTargyak(): Observable<Object> {
    return this.http.get(this.baseUrl + 'targy');
  }

}
