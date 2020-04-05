import {Component, OnInit} from '@angular/core';
import {NotificationService} from '../error-notification-module/service/notification.service';
import {QueryService} from './service/query.service';
import {ErrorModel} from '../error-notification-module/model/ErrorModel';

@Component({
    selector: 'app-query-execute',
    templateUrl: './query-execute.component.html',
    styleUrls: ['./query-execute.component.sass']
})
export class QueryExecuteComponent implements OnInit {
    public query = 'xquery version \"3.1\";';

    constructor(
        private notificationService: NotificationService,
        private queryService: QueryService
    ) {
    }

    ngOnInit() {
    }

    onExecute() {
        this.queryService.evalXqueryFromString(this.query).subscribe(
            result => {
                this.notificationService.result(result);
            },
            error => {
                const jsonError: ErrorModel = JSON.parse(error.error);
                this.notificationService.Error(jsonError);
            }
        );
    }

}
