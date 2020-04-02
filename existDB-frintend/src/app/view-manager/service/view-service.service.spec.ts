import { TestBed } from '@angular/core/testing';

import { ViewServiceService } from './view-service.service';

describe('ViewServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ViewServiceService = TestBed.get(ViewServiceService);
    expect(service).toBeTruthy();
  });
});
