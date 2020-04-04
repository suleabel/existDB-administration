import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryResultDialogComponent } from './query-result-dialog.component';

describe('QueryResultDialogComponent', () => {
  let component: QueryResultDialogComponent;
  let fixture: ComponentFixture<QueryResultDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QueryResultDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueryResultDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
