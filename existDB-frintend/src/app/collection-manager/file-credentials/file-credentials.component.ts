import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GroupsService} from '../../exist-group-manager/service/groups.service';
import {UserService} from '../../user-manager-module/service/user.service';
import {NotificationService} from '../../error-dialog/service/notification.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FileExplorerService} from '../service/file-explorer.service';

@Component({
    selector: 'app-file-credentials',
    templateUrl: './file-credentials.component.html',
    styleUrls: ['./file-credentials.component.sass']
})
export class FileCredentialsComponent implements OnInit {
    public editFileForm: FormGroup;
    public existUsers: string[];
    public existGroups: string[];
    public isEdit = false;


    constructor(
        public dialogRef: MatDialogRef<FileCredentialsComponent>,
        @Inject(MAT_DIALOG_DATA) public data,
        private groupService: GroupsService,
        private userService: UserService,
        private formBuilder: FormBuilder,
        private notificationService: NotificationService,
        private collectionService: FileExplorerService) {
    }

    ngOnInit() {
        this.getAllExistGroup();
        this.getAllExistUser();
        this.editFileForm = this.formBuilder.group({
            name: [this.data.res.name, Validators.required],
            path: [this.data.res.path, Validators.required],
            owner: [this.data.res.owner, Validators.required],
            group: [this.data.res.group, Validators.required],
            mode: [this.data.res.mode, Validators.required],
            date: [this.data.res.date, Validators.required],
            resource: [this.data.res.resource, Validators.required],
        });
    }

    getAllExistUser() {
        this.groupService.getUsersNames()
            .subscribe(
                data => {
                    this.existUsers = data;
                    this.notificationService.success('Success');
                },
                error => {
                    this.notificationService.warn(error.error.message);
                }
            );
    }

    getAllExistGroup() {
        this.userService.getGroupsNames()
            .subscribe(
                data => {
                    this.existGroups = data;
                },
                error => {
                    this.notificationService.warn(error.error.message);
                }
            );
    }

    unlockResource(res) {
        this.collectionService.unlockResource(res)
            .subscribe(
                data => {
                    this.notificationService.success('success');
                },
                error => {
                    this.notificationService.warn(error.error.message);
                });
    }

    onClose() {
        this.dialogRef.close();
    }

    get name() {
        return this.editFileForm.get('name');
    }

    get path() {
        return this.editFileForm.get('path');
    }

    get owner() {
        return this.editFileForm.get('owner');
    }

    get group() {
        return this.editFileForm.get('group');
    }

    get mode() {
        return this.editFileForm.get('mode');
    }

    get date() {
        return this.editFileForm.get('date');
    }

}
