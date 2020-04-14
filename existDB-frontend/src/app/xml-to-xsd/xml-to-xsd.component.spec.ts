import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { XmlToXsdComponent } from './xml-to-xsd.component';

describe('XmlToXsdComponent', () => {
  let component: XmlToXsdComponent;
  let fixture: ComponentFixture<XmlToXsdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ XmlToXsdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(XmlToXsdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
