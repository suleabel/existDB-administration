import { TestBed } from '@angular/core/testing';

import { TargyakService } from './targyak.service';

describe('TargyakService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TargyakService = TestBed.get(TargyakService);
    expect(service).toBeTruthy();
  });
});
