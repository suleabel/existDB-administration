import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseSaveLocationComponent } from './browse-save-location.component';

describe('BrowseSaveLocationComponent', () => {
  let component: BrowseSaveLocationComponent;
  let fixture: ComponentFixture<BrowseSaveLocationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrowseSaveLocationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrowseSaveLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
