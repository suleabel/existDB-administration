import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistAddUserComponent } from './exist-add-user.component';

describe('ExistAddUserComponent', () => {
  let component: ExistAddUserComponent;
  let fixture: ComponentFixture<ExistAddUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExistAddUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExistAddUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
