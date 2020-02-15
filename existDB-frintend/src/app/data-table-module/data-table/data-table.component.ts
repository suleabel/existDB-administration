import {Component, OnInit, ViewChild} from '@angular/core';
import {TableDataService} from '../services/table-data.service';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.sass']
})
export class DataTableComponent implements OnInit {

  MyDataSource: any;
  displayedColumns = ['id', 'userId', 'title', 'body'];

  constructor(public tableDataService: TableDataService) {
  }
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.RenderDataTable();
  }

  RenderDataTable() {
    this.tableDataService.GetAllRecords()
      .subscribe(
        res => {
          this.MyDataSource = new MatTableDataSource();
          this.MyDataSource.data = res;
          this.MyDataSource.sort = this.sort;
          this.MyDataSource.paginator = this.paginator;
          console.log(this.MyDataSource.data);
        },
        error => {
          console.log('Error');
        });
  }

}
