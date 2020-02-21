import {Component, OnInit, ViewChild} from '@angular/core';
import {TargyakService} from '../service/targyak.service';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-targyak-lista',
  templateUrl: './targyak-lista.component.html',
  styleUrls: ['./targyak-lista.component.sass']
})
export class TargyakListaComponent implements OnInit {

  MyDataSource: any;
  displayedColumns = ['targykod', 'felev', 'targynev', 'targy_angol_neve', 'Ea', 'Gy', 'Kov', 'Kr', 'ETF', 'helyettesitotargy_kodja'];

  constructor(public targyakService: TargyakService) {
  }

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    // this.RenderTargyakTable();
  }

  RenderTargyakTable() {
    this.targyakService.getTargyak()
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
        }
      );
  }

}
