import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {GroupsService} from './service/groups.service';

@Component({
  selector: 'app-exist-group-list',
  templateUrl: './exist-group-list.component.html',
  styleUrls: ['./exist-group-list.component.sass']
})
export class ExistGroupListComponent implements OnInit {
  GroupsData: any;
  post: {
    name,
    manager,
    desc,
    members
  };
  displayedColumns = ['name', 'manager', 'desc', 'members', 'details', 'delete'];
  constructor(public groupsServices: GroupsService, private router: Router) { }

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.RenderGroupList();
  }

  RenderGroupList() {
    this.groupsServices.getExistGroups()
        .subscribe(
            res => {
              this.GroupsData = new MatTableDataSource();
              this.GroupsData.data = res;
              this.GroupsData.sort = this.sort;
              this.GroupsData.paginator = this.paginator;
              console.log(this.GroupsData.data);
            },
            error => {
              console.log('Error: ' + error);
            }
        );
  }

  details(groupName) {

  }

  delete(groupName) {

  }

}
