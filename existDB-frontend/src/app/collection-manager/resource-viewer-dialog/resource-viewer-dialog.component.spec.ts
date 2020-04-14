import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourceViewerDialogComponent } from './resource-viewer-dialog.component';

describe('ResourceViewerDialogComponent', () => {
  let component: ResourceViewerDialogComponent;
  let fixture: ComponentFixture<ResourceViewerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceViewerDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceViewerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
