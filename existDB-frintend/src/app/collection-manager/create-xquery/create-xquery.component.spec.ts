import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateXqueryComponent } from './create-xquery.component';

describe('CreateXqueryComponent', () => {
  let component: CreateXqueryComponent;
  let fixture: ComponentFixture<CreateXqueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateXqueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateXqueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
