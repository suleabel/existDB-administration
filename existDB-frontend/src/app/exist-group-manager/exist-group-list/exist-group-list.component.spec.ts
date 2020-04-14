import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistGroupListComponent } from './exist-group-list.component';

describe('ExistGroupListComponent', () => {
  let component: ExistGroupListComponent;
  let fixture: ComponentFixture<ExistGroupListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExistGroupListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExistGroupListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
