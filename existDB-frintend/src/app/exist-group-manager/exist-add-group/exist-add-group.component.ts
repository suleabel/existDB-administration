import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GroupsService} from '../service/groups.service';
import {ExistGroupModel} from '../model/existGroup.model';

@Component({
  selector: 'app-exist-add-group',
  templateUrl: './exist-add-group.component.html',
  styleUrls: ['./exist-add-group.component.sass']
})
export class ExistAddGroupComponent implements OnInit {
  public addGroupForm: FormGroup;
  public addGroupData: ExistGroupModel;
  public existUsers: [];

  constructor(
      private formBuilder: FormBuilder,
      private groupService: GroupsService) { }

  ngOnInit() {
      this.groupService.getUsersNames()
          .subscribe(
              data => {
                  this.existUsers = data;
              },
              error => {
                  console.log('Error: ' + error);
              }
          );

      this.addGroupForm = this.formBuilder.group({
        groupName: [null, Validators.required],
        groupManager: [null],
        groupMembers: [null, Validators.required],
        desc: []
  });
      console.log(this.addGroupForm);
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
  get groupMembers() {
      return this.addGroupForm.get('groupMembers');
  }

  submit() {
    this.addGroupData = this.addGroupForm.value;
    console.log(this.addGroupData);
    this.groupService.addGroupToExist(this.addGroupData).subscribe(
        data => {
          console.log('success: ' + data);
          this.addGroupForm.reset();
        },
        error => {
          console.log('Error: ' + error.error.message);
        }
    );
    location.reload();
  }
}
