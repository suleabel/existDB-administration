import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistUsersEditDetailsComponent } from './exist-users-edit-details.component';

describe('ExistUsersEditDetailsComponent', () => {
  let component: ExistUsersEditDetailsComponent;
  let fixture: ComponentFixture<ExistUsersEditDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExistUsersEditDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExistUsersEditDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
