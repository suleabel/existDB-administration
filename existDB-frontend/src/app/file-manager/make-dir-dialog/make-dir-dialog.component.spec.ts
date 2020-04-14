import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeDirDialogComponent } from './make-dir-dialog.component';

describe('MakeDirDialogComponent', () => {
  let component: MakeDirDialogComponent;
  let fixture: ComponentFixture<MakeDirDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeDirDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeDirDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
