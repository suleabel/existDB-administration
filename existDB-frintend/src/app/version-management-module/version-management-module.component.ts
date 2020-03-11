import { Component, OnInit } from '@angular/core';
import {VersionManagementService} from './service/version-management.service';

@Component({
  selector: 'app-version-management-module',
  templateUrl: './version-management-module.component.html',
  styleUrls: ['./version-management-module.component.sass']
})
export class VersionManagementModuleComponent implements OnInit {
  public versionIsAvailable: boolean;

  constructor(private versionManagementService: VersionManagementService) { }

  ngOnInit() {
    this.versionIsAvailable = false;
    this.versionManagementService.versionManagerIsActivated()
        .subscribe(data => {
          console.log(data);
        }, error => {
            console.log(error);
        });
  }

  activateVersionManagement() {
    window.location.reload();
  }

  deactivateVersionManagement() {
    window.location.reload();
  }

}
