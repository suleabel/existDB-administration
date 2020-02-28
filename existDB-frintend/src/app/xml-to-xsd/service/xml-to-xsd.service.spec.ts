import { TestBed } from '@angular/core/testing';

import { XmlToXsdService } from './xml-to-xsd.service';

describe('XmlToXsdService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: XmlToXsdService = TestBed.get(XmlToXsdService);
    expect(service).toBeTruthy();
  });
});
