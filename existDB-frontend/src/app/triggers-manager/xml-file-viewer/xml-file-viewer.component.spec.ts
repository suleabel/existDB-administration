import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { XmlFileViewerComponent } from './xml-file-viewer.component';

describe('XmlFileViewerComponent', () => {
  let component: XmlFileViewerComponent;
  let fixture: ComponentFixture<XmlFileViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ XmlFileViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(XmlFileViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
