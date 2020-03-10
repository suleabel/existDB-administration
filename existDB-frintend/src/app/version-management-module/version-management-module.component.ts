import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-version-management-module',
  templateUrl: './version-management-module.component.html',
  styleUrls: ['./version-management-module.component.sass']
})
export class VersionManagementModuleComponent implements OnInit {
  public versionIsAvailable: boolean;

  constructor() { }

  ngOnInit() {
    this.versionIsAvailable = false;
  }

  activateVersionManagement() {
    window.location.reload();
  }

  deactivateVersionManagement() {
    window.location.reload();
  }

}
