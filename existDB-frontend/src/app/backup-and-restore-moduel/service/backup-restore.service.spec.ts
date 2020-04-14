import { TestBed } from '@angular/core/testing';

import { BackupRestoreService } from './backup-restore.service';

describe('BackupRestoreService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BackupRestoreService = TestBed.get(BackupRestoreService);
    expect(service).toBeTruthy();
  });
});
