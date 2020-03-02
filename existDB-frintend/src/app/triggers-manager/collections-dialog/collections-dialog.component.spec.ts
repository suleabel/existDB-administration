import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionsDialogComponent } from './collections-dialog.component';

describe('CollectionsDialogComponent', () => {
  let component: CollectionsDialogComponent;
  let fixture: ComponentFixture<CollectionsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollectionsDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollectionsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
