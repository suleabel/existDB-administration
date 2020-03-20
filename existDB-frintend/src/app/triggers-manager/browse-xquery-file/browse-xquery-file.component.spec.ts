import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseXqueryFileComponent } from './browse-xquery-file.component';

describe('BrowseXqueryFileComponent', () => {
  let component: BrowseXqueryFileComponent;
  let fixture: ComponentFixture<BrowseXqueryFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrowseXqueryFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrowseXqueryFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
