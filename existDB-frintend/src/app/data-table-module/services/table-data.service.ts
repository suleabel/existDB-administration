import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TableDataService {

  private headers = new Headers({'Content-Type': 'application/json'});
  baseUrl = '';

  constructor(private http: HttpClient) {
    this.baseUrl = 'https://jsonplaceholder.typicode.com/';
  }

  public GetAllRecords() {
    return this.http.get(this.baseUrl + 'posts');
  }
}
