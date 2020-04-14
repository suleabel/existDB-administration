import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BackupAndRestoreModuleComponent } from './backup-and-restore-module.component';

describe('BackupAndRestoreModuleComponent', () => {
  let component: BackupAndRestoreModuleComponent;
  let fixture: ComponentFixture<BackupAndRestoreModuleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BackupAndRestoreModuleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BackupAndRestoreModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
