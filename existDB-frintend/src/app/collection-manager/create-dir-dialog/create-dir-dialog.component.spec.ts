import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDirDialogComponent } from './create-dir-dialog.component';

describe('CreateDirDialogComponent', () => {
  let component: CreateDirDialogComponent;
  let fixture: ComponentFixture<CreateDirDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateDirDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateDirDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
