import { Component, OnInit } from '@angular/core';
import {VersionManagementService} from './service/version-management.service';
import {NotificationService} from '../error-dialog/service/notification.service';

@Component({
  selector: 'app-version-management-module',
  templateUrl: './version-management-module.component.html',
  styleUrls: ['./version-management-module.component.sass']
})
export class VersionManagementModuleComponent implements OnInit {
  public versionIsAvailable: boolean;

  constructor(private versionManagementService: VersionManagementService,
              private notifiationService: NotificationService) { }

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
      this.versionManagementService.enableVersionManager()
          .subscribe(data => {
              this.notifiationService.success('Version Management is enabled');
          },
              error => {
              this.notifiationService.warn('Error: ' + error);
              });
  }

  deactivateVersionManagement() {
    window.location.reload();
  }

}
