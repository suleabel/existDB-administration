import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {GroupsService} from '../service/groups.service';
import {ExistGroupModel} from '../model/existGroup.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {stringify} from 'querystring';

@Component({
  selector: 'app-exist-group-details',
  templateUrl: './exist-group-details.component.html',
  styleUrls: ['./exist-group-details.component.sass']
})
export class ExistGroupDetailsComponent implements OnInit {
  private editGroupForm: FormGroup;
  groupDetailsEdit = false;
  selectedGroup: ExistGroupModel = null;
  private existUsers: string[];
  private editGroupsData: ExistGroupModel;

  constructor(
      private groupService: GroupsService,
      private formBuilder: FormBuilder,
      private router: Router) { }

  ngOnInit() {
    console.log('selectedGroup:' + stringify(this.groupService.getSelectedGroup()));
    this.selectedGroup = this.groupService.getSelectedGroup();
    if (this.selectedGroup === null) {
      this.router.navigateByUrl('/exist-group-manager');
    }
    this.getAllExistUser();
    this.editGroupForm = this.formBuilder.group({
      name: [this.selectedGroup.groupName, Validators.required],
      manager: [this.selectedGroup.groupManager],
      desc: [this.selectedGroup.desc],
      members: [this.selectedGroup.groupMembers, Validators.required]
    });
  }

  save() {
    this.editGroupsData = this.editGroupForm.value;
    if (!this.editGroupsData.groupMembers.includes(this.editGroupsData.groupManager)) {
      this.editGroupsData.groupMembers.push(this.editGroupsData.groupManager);
    }
    console.log(this.editGroupsData);
  }

  getAllExistUser() {
    this.groupService.getUsersNames()
        .subscribe(
            data => {
              this.existUsers = data;
            },
              error => {
                console.log('Error: ' + error);
            }
        );
  }

  get name() {
    return this.editGroupForm.get('name');
  }

  get desc() {
    return this.editGroupForm.get('desc');
  }

  get manager() {
    return this.editGroupForm.get('manager');
  }

  get members() {
    return this.editGroupForm.get('members');
  }

}
