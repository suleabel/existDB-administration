import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TargyakListaComponent } from './targyak-lista.component';

describe('TargyakListaComponent', () => {
  let component: TargyakListaComponent;
  let fixture: ComponentFixture<TargyakListaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TargyakListaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TargyakListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
