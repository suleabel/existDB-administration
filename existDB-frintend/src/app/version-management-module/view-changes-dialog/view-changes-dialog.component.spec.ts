import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewChangesDialogComponent } from './view-changes-dialog.component';

describe('ViewChangesDialogComponent', () => {
  let component: ViewChangesDialogComponent;
  let fixture: ComponentFixture<ViewChangesDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewChangesDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewChangesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
