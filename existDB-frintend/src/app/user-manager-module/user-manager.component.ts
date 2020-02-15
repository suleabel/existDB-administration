import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../auth-module/auth.service';
import {SignUpInfo} from '../auth-module/signup-info';

@Component({
  selector: 'app-user-manager',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.sass']
})
export class UserManagerComponent implements OnInit {


  constructor() {
  }

  ngOnInit() {
  }
}
