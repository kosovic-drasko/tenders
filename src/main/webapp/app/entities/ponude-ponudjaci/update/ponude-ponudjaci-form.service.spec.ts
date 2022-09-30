import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ponude-ponudjaci.test-samples';

import { PonudePonudjaciFormService } from './ponude-ponudjaci-form.service';

describe('PonudePonudjaci Form Service', () => {
  let service: PonudePonudjaciFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PonudePonudjaciFormService);
  });

  describe('Service methods', () => {
    describe('createPonudePonudjaciFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPonudePonudjaciFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sifraPostupka: expect.any(Object),
            sifraPonude: expect.any(Object),
            brojPartije: expect.any(Object),
            nazivProizvodjaca: expect.any(Object),
            nazivPonudjaca: expect.any(Object),
            zasticeniNaziv: expect.any(Object),
            ponudjenaVrijednost: expect.any(Object),
            rokIsporuke: expect.any(Object),
            jedinicnaCijena: expect.any(Object),
            selected: expect.any(Object),
          })
        );
      });

      it('passing IPonudePonudjaci should create a new form with FormGroup', () => {
        const formGroup = service.createPonudePonudjaciFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sifraPostupka: expect.any(Object),
            sifraPonude: expect.any(Object),
            brojPartije: expect.any(Object),
            nazivProizvodjaca: expect.any(Object),
            nazivPonudjaca: expect.any(Object),
            zasticeniNaziv: expect.any(Object),
            ponudjenaVrijednost: expect.any(Object),
            rokIsporuke: expect.any(Object),
            jedinicnaCijena: expect.any(Object),
            selected: expect.any(Object),
          })
        );
      });
    });

    describe('getPonudePonudjaci', () => {
      it('should return NewPonudePonudjaci for default PonudePonudjaci initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPonudePonudjaciFormGroup(sampleWithNewData);

        const ponudePonudjaci = service.getPonudePonudjaci(formGroup) as any;

        expect(ponudePonudjaci).toMatchObject(sampleWithNewData);
      });

      it('should return NewPonudePonudjaci for empty PonudePonudjaci initial value', () => {
        const formGroup = service.createPonudePonudjaciFormGroup();

        const ponudePonudjaci = service.getPonudePonudjaci(formGroup) as any;

        expect(ponudePonudjaci).toMatchObject({});
      });

      it('should return IPonudePonudjaci', () => {
        const formGroup = service.createPonudePonudjaciFormGroup(sampleWithRequiredData);

        const ponudePonudjaci = service.getPonudePonudjaci(formGroup) as any;

        expect(ponudePonudjaci).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPonudePonudjaci should not enable id FormControl', () => {
        const formGroup = service.createPonudePonudjaciFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPonudePonudjaci should disable id FormControl', () => {
        const formGroup = service.createPonudePonudjaciFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
