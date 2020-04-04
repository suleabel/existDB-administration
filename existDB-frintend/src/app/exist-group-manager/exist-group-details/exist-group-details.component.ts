import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {GroupsService} from '../service/groups.service';
import {ExistGroupModel} from '../model/existGroup.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {stringify} from 'querystring';
import {NotificationService} from '../../error-notification-module/service/notification.service';

@Component({
    selector: 'app-exist-group-details',
    templateUrl: './exist-group-details.component.html',
    styleUrls: ['./exist-group-details.component.sass']
})
export class ExistGroupDetailsComponent implements OnInit {
    public editGroupForm: FormGroup;
    public groupDetailsEdit = false;
    public selectedGroup: ExistGroupModel = null;
    public existUsers: string[];
    public editGroupsData: ExistGroupModel;

    constructor(
        private groupService: GroupsService,
        private formBuilder: FormBuilder,
        private router: Router,
        private notificationService: NotificationService) {
    }

    ngOnInit() {
        console.log('selectedGroup:' + stringify(this.groupService.getSelectedGroup()));
        this.selectedGroup = this.groupService.getSelectedGroup();
        if (this.selectedGroup === null) {
            this.router.navigateByUrl('/exist-group-manager');
        }
        this.getAllExistUser();
        this.editGroupForm = this.formBuilder.group({
            groupName: [this.selectedGroup.groupName, Validators.required],
            groupManager: [this.selectedGroup.groupManager],
            desc: [this.selectedGroup.desc],
            groupMembers: [this.selectedGroup.groupMembers, Validators.required]
        });
    }

    save() {
        this.editGroupsData = this.editGroupForm.value;
        if (!this.editGroupsData.groupMembers.includes(this.editGroupsData.groupManager)) {
            this.editGroupsData.groupMembers.push(this.editGroupsData.groupManager);
        }
        this.groupService.editGroups(this.editGroupsData)
            .subscribe(data => {
                    console.log('Saved');
                    this.notificationService.success('Success');
                },
                error => {
                    this.notificationService.Error(error.error);
                });
    }

    getAllExistUser() {
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

    get groupName() {
        return this.editGroupForm.get('groupName');
    }

    get desc() {
        return this.editGroupForm.get('desc');
    }

    get groupManager() {
        return this.editGroupForm.get('groupManager');
    }

    get groupMembers() {
        return this.editGroupForm.get('groupMembers');
    }
}
