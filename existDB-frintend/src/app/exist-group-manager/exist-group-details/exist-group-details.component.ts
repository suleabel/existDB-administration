import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {GroupsService} from '../service/groups.service';
import {ExistGroupModel} from '../model/existGroup.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

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
    this.selectedGroup = this.groupService.getSelectedGroup();
    if (this.selectedGroup === null) {
      this.router.navigateByUrl('/exist-group-manager');
    }
    this.getAllExistUser();
    this.editGroupForm = this.formBuilder.group({
      name: [this.selectedGroup.name, Validators.required],
      manager: [this.selectedGroup.manager],
      desc: [this.selectedGroup.desc],
      members: [this.selectedGroup.members, Validators.required]
    });
  }

  save() {
    this.editGroupsData = this.editGroupForm.value;
    if (!this.editGroupsData.members.includes(this.editGroupsData.manager)) {
      this.editGroupsData.members.push(this.editGroupsData.manager);
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
