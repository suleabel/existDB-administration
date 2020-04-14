import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileCredentialsComponent } from './file-credentials.component';

describe('FileCredentialsComponent', () => {
  let component: FileCredentialsComponent;
  let fixture: ComponentFixture<FileCredentialsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileCredentialsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileCredentialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
