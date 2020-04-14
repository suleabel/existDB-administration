import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvalResultViewerComponent } from './eval-result-viewer.component';

describe('EvalResultViewerComponent', () => {
  let component: EvalResultViewerComponent;
  let fixture: ComponentFixture<EvalResultViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvalResultViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvalResultViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
