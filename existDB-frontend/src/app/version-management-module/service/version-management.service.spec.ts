import { TestBed } from '@angular/core/testing';

import { VersionManagementService } from './version-management.service';

describe('VersionManagementService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: VersionManagementService = TestBed.get(VersionManagementService);
    expect(service).toBeTruthy();
  });
});
