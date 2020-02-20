import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistUsersListComponent } from './exist-users-list.component';

describe('ExistUsersListComponent', () => {
  let component: ExistUsersListComponent;
  let fixture: ComponentFixture<ExistUsersListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExistUsersListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExistUsersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
