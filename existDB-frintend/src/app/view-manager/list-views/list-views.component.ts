import {Component, OnInit, ViewChild} from '@angular/core';
import {ViewServiceService} from '../service/view-service.service';
import {BehaviorSubject} from 'rxjs';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {CreatedViewModel} from '../model/CreatedViewModel';

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

    // @ts-ignore
    @ViewChild(MatPaginator) paginator: MatPaginator;
    // @ts-ignore
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