import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PmMainComponent } from './pm-main.component';

describe('PmMainComponent', () => {
  let component: PmMainComponent;
  let fixture: ComponentFixture<PmMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PmMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PmMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
