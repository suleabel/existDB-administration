import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionManagementModuleComponent } from './version-management-module.component';

describe('VersionManagementModuleComponent', () => {
  let component: VersionManagementModuleComponent;
  let fixture: ComponentFixture<VersionManagementModuleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VersionManagementModuleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VersionManagementModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
