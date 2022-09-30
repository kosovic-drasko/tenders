import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPonudePonudjaci } from '../ponude-ponudjaci.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ponude-ponudjaci.test-samples';

import { PonudePonudjaciService } from './ponude-ponudjaci.service';

const requireRestSample: IPonudePonudjaci = {
  ...sampleWithRequiredData,
};

describe('PonudePonudjaci Service', () => {
  let service: PonudePonudjaciService;
  let httpMock: HttpTestingController;
  let expectedResult: IPonudePonudjaci | IPonudePonudjaci[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PonudePonudjaciService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PonudePonudjaci', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const ponudePonudjaci = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ponudePonudjaci).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PonudePonudjaci', () => {
      const ponudePonudjaci = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ponudePonudjaci).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PonudePonudjaci', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PonudePonudjaci', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PonudePonudjaci', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPonudePonudjaciToCollectionIfMissing', () => {
      it('should add a PonudePonudjaci to an empty array', () => {
        const ponudePonudjaci: IPonudePonudjaci = sampleWithRequiredData;
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing([], ponudePonudjaci);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ponudePonudjaci);
      });

      it('should not add a PonudePonudjaci to an array that contains it', () => {
        const ponudePonudjaci: IPonudePonudjaci = sampleWithRequiredData;
        const ponudePonudjaciCollection: IPonudePonudjaci[] = [
          {
            ...ponudePonudjaci,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing(ponudePonudjaciCollection, ponudePonudjaci);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PonudePonudjaci to an array that doesn't contain it", () => {
        const ponudePonudjaci: IPonudePonudjaci = sampleWithRequiredData;
        const ponudePonudjaciCollection: IPonudePonudjaci[] = [sampleWithPartialData];
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing(ponudePonudjaciCollection, ponudePonudjaci);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ponudePonudjaci);
      });

      it('should add only unique PonudePonudjaci to an array', () => {
        const ponudePonudjaciArray: IPonudePonudjaci[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ponudePonudjaciCollection: IPonudePonudjaci[] = [sampleWithRequiredData];
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing(ponudePonudjaciCollection, ...ponudePonudjaciArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ponudePonudjaci: IPonudePonudjaci = sampleWithRequiredData;
        const ponudePonudjaci2: IPonudePonudjaci = sampleWithPartialData;
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing([], ponudePonudjaci, ponudePonudjaci2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ponudePonudjaci);
        expect(expectedResult).toContain(ponudePonudjaci2);
      });

      it('should accept null and undefined values', () => {
        const ponudePonudjaci: IPonudePonudjaci = sampleWithRequiredData;
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing([], null, ponudePonudjaci, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ponudePonudjaci);
      });

      it('should return initial array if no PonudePonudjaci is added', () => {
        const ponudePonudjaciCollection: IPonudePonudjaci[] = [sampleWithRequiredData];
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing(ponudePonudjaciCollection, undefined, null);
        expect(expectedResult).toEqual(ponudePonudjaciCollection);
      });
    });

    describe('comparePonudePonudjaci', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePonudePonudjaci(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePonudePonudjaci(entity1, entity2);
        const compareResult2 = service.comparePonudePonudjaci(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePonudePonudjaci(entity1, entity2);
        const compareResult2 = service.comparePonudePonudjaci(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePonudePonudjaci(entity1, entity2);
        const compareResult2 = service.comparePonudePonudjaci(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
