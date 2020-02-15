import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserManagerSidenemuComponent } from './user-manager-sidenemu.component';

describe('UserManagerSidenemuComponent', () => {
  let component: UserManagerSidenemuComponent;
  let fixture: ComponentFixture<UserManagerSidenemuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserManagerSidenemuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserManagerSidenemuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
