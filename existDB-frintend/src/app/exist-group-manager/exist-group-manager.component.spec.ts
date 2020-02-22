import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistGroupManagerComponent } from './exist-group-manager.component';

describe('ExistGroupManagerComponent', () => {
  let component: ExistGroupManagerComponent;
  let fixture: ComponentFixture<ExistGroupManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExistGroupManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExistGroupManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
