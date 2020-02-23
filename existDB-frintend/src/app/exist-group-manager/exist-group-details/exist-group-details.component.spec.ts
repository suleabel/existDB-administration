import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistGroupDetailsComponent } from './exist-group-details.component';

describe('ExistGroupDetailsComponent', () => {
  let component: ExistGroupDetailsComponent;
  let fixture: ComponentFixture<ExistGroupDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExistGroupDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExistGroupDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
