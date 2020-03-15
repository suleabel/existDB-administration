import {Component, OnInit} from '@angular/core';
import {FileExplorerService} from '../service/file-explorer.service';
import {Router} from '@angular/router';
import {Credentials} from '../model/Credentials';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GroupsService} from '../../exist-group-manager/service/groups.service';
import {UserService} from '../../user-manager-module/service/user.service';
import {NotificationService} from '../../error-dialog/service/notification.service';

@Component({
    selector: 'app-file-credentials',
    templateUrl: './file-credentials.component.html',
    styleUrls: ['./file-credentials.component.sass']
})
export class FileCredentialsComponent implements OnInit {
    public editFileForm: FormGroup;
    public editFileData: Credentials;

    public existUsers: string[];
    public existGroups: string[];

    public selectedFileCredentials: Credentials = null;
    public isEdit = false;


    constructor(private fileExplorerService: FileExplorerService,
                private router: Router,
                private groupService: GroupsService,
                private userService: UserService,
                private formBuilder: FormBuilder,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.selectedFileCredentials = this.fileExplorerService.getEditedFileCredentials();
        if (this.selectedFileCredentials === null || this.selectedFileCredentials === undefined) {
            this.router.navigateByUrl('/collection-manager');
        }
        this.getAllExistGroup();
        this.getAllExistUser();

        this.editFileForm = this.formBuilder.group({
            name: [this.selectedFileCredentials.name, Validators.required],
            path: [this.selectedFileCredentials.path, Validators.required],
            owner: [this.selectedFileCredentials.owner, Validators.required],
            group: [this.selectedFileCredentials.group, Validators.required],
            mode: [this.selectedFileCredentials.mode, Validators.required],
            date: [this.selectedFileCredentials.date, Validators.required],
            writable: [this.selectedFileCredentials.writable, Validators.required],
            resource: [this.selectedFileCredentials.resource, Validators.required],
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
                    this.notificationService.warn('Error: ' + error);
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
                    console.log('Error: ' + error);
                }
            );

    }

    save() {
        this.editFileData = this.editFileForm.value;
        this.fileExplorerService.editFileCredentials(this.editFileData)
            .subscribe(data => {
                console.log(data);
            },
            error => {
                console.log(error);
            });
        console.log(this.editFileData);
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
