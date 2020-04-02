import { Component, OnInit } from '@angular/core';
import {ViewServiceService} from './service/view-service.service';
import {CreateViewModel} from './model/CreateViewModel';

@Component({
  selector: 'app-view-manager',
  templateUrl: './view-manager.component.html',
  styleUrls: ['./view-manager.component.sass']
})
export class ViewManagerComponent implements OnInit {

  constructor(private viewService: ViewServiceService) { }

  ngOnInit() {
    const viewModel: CreateViewModel = {viewCollection: '/db/testData', viewName: 'test_view.xml', queryExpression: 'doc("/db/testData/books.xml")/bookstore/book[price<30]'};
    this.viewService.createView(viewModel).subscribe(
        data => {
          console.log(data);
        },
        error => {
          console.log(error);
        }
    );
  }

}
