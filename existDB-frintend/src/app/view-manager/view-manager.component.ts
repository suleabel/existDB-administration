import {Component, OnInit} from '@angular/core';
import {ViewServiceService} from './service/view-service.service';
import {CreateViewModel} from './model/CreateViewModel';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NotificationService} from '../error-notification-module/service/notification.service';
import {QueryService} from '../query-execute/service/query.service';
import {ErrorModel} from '../error-notification-module/model/ErrorModel';
import {BehaviorSubject} from 'rxjs';

@Component({
    selector: 'app-view-manager',
    templateUrl: './view-manager.component.html',
    styleUrls: ['./view-manager.component.sass']
})
export class ViewManagerComponent implements OnInit {
    public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public viewData: CreateViewModel;
    public viewForm: FormGroup;
    public queryIsChecked = false;

    constructor(private viewService: ViewServiceService,
                private notificationService: NotificationService,
                private formBuilder: FormBuilder,
                private queryService: QueryService) {
    }

    ngOnInit() {
        this.buildForm();
    }

    buildForm() {
        this.viewForm = this.formBuilder.group({
            viewCollection: ['', Validators.required],
            viewName: ['', Validators.required],
            queryExpression: ['xquery version "3.1";', Validators.required],
        });
    }

    onSave() {
        this.isLoading$.next(true);
        this.viewService.createView(this.viewForm.value).subscribe(data => {
                this.notificationService.success('View saved, database is reindex');
                this.isLoading$.next(false);
            },
            error => {
                this.notificationService.Error(error.error);
                this.isLoading$.next(false);
            });
    }

    onCheck() {
        this.queryService.evalXqueryFromString(this.viewForm.value.queryExpression).subscribe(
            data => {
                this.notificationService.result(data);
                this.queryIsChecked = true;
            },
            error => {
                const jsonError: ErrorModel = JSON.parse(error.error);
                this.notificationService.Error(jsonError);
            }
        );
    }

    get viewCollection() {
        return this.viewForm.get('viewCollection');
    }

    get viewName() {
        return this.viewForm.get('viewName');
    }

    get queryExpression() {
        return this.viewForm.get('queryExpression');
    }

}
