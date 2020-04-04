import {Component, OnInit} from '@angular/core';
import {ViewServiceService} from './service/view-service.service';
import {CreateViewModel} from './model/CreateViewModel';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NotificationService} from '../error-notification-module/service/notification.service';
import {QueryService} from '../query-execute/service/query.service';

@Component({
    selector: 'app-view-manager',
    templateUrl: './view-manager.component.html',
    styleUrls: ['./view-manager.component.sass']
})
export class ViewManagerComponent implements OnInit {
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
            queryExpression: ['', Validators.required],
        });
    }

    onSave() {
        console.log(this.viewForm.value);
    }

    onCheck() {
        this.queryService.evalXqueryFromString(this.viewForm.value.queryExpression).subscribe(
            data => {
                this.notificationService.result(data.response);
                this.queryIsChecked = true;
            },
            error => {
                this.notificationService.Error(error.error);
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
