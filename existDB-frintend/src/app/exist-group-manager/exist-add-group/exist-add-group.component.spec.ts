import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistAddGroupComponent } from './exist-add-group.component';

describe('ExistAddGroupComponent', () => {
  let component: ExistAddGroupComponent;
  let fixture: ComponentFixture<ExistAddGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExistAddGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExistAddGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
