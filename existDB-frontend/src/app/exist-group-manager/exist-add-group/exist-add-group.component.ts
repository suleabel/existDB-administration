import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GroupsService} from '../service/groups.service';
import {NotificationService} from '../../error-notification-module/service/notification.service';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
    selector: 'app-exist-add-group',
    templateUrl: './exist-add-group.component.html',
    styleUrls: ['./exist-add-group.component.sass']
})
export class ExistAddGroupComponent implements OnInit {
    public addGroupForm: FormGroup;
    public existUsers: [];

    constructor(
        private formBuilder: FormBuilder,
        private groupService: GroupsService,
        private notificationService: NotificationService,
        private dialogRef: MatDialogRef<ExistAddGroupComponent>) {
    }

    ngOnInit() {
        this.getUsersNames();
        this.addGroupForm = this.formBuilder.group({
            groupName: [null, Validators.required],
            groupManager: [null],
            desc: []
        });
    }

    getUsersNames() {
        this.groupService.getUsersNames()
            .subscribe(
                data => {
                    this.existUsers = data;
                },
                error => {
                    this.notificationService.Error(error.error);
                }
            );
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    get groupName() {
        return this.addGroupForm.get('groupName');
    }

    get groupManager() {
        return this.addGroupForm.get('groupManager');
    }

    get desc() {
        return this.addGroupForm.get('desc');
    }
}
