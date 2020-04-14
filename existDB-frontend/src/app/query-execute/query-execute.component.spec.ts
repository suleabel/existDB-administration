import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryExecuteComponent } from './query-execute.component';

describe('QueryExecuteComponent', () => {
  let component: QueryExecuteComponent;
  let fixture: ComponentFixture<QueryExecuteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QueryExecuteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueryExecuteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
