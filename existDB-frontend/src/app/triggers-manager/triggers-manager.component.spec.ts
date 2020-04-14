import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TriggersManagerComponent } from './triggers-manager.component';

describe('TriggersManagerComponent', () => {
  let component: TriggersManagerComponent;
  let fixture: ComponentFixture<TriggersManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TriggersManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TriggersManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
