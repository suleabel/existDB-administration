import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewResourceComponent } from './create-new-resource.component';

describe('CreateNewResourceComponent', () => {
  let component: CreateNewResourceComponent;
  let fixture: ComponentFixture<CreateNewResourceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewResourceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewResourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
