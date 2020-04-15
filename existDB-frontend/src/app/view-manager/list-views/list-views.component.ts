import {Component, OnInit, ViewChild} from '@angular/core';
import {ViewServiceService} from '../service/view-service.service';
import {BehaviorSubject} from 'rxjs';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {CreatedViewModel} from '../model/CreatedViewModel';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';

@Component({
    selector: 'app-list-views',
    templateUrl: './list-views.component.html',
    styleUrls: ['./list-views.component.sass']
})
export class ListViewsComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public TableData: any;
    public element: CreatedViewModel;
    public displayedColumns = ['user', 'date', 'configLocation', 'queryName', 'viewLocation'];

    constructor(private viewService: ViewServiceService,
                private notificationService: NotificationService) {
    }

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.renderData();
    }

    renderData() {
        this.isLoading$.next(true);
        this.viewService.getCreatedViews().subscribe(
            data => {
                console.log(data);
                this.TableData = new MatTableDataSource(data);
                this.TableData.sort = this.sort;
                this.TableData.paginator = this.paginator;
                this.isLoading$.next(false);
            }, error => {
                this.notificationService.Error(error.error);
            }
        );
    }

}
