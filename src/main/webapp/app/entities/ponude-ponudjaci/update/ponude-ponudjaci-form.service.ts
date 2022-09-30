import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPonudePonudjaci, NewPonudePonudjaci } from '../ponude-ponudjaci.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPonudePonudjaci for edit and NewPonudePonudjaciFormGroupInput for create.
 */
type PonudePonudjaciFormGroupInput = IPonudePonudjaci | PartialWithRequiredKeyOf<NewPonudePonudjaci>;

type PonudePonudjaciFormDefaults = Pick<NewPonudePonudjaci, 'id' | 'selected'>;

type PonudePonudjaciFormGroupContent = {
  id: FormControl<IPonudePonudjaci['id'] | NewPonudePonudjaci['id']>;
  sifraPostupka: FormControl<IPonudePonudjaci['sifraPostupka']>;
  sifraPonude: FormControl<IPonudePonudjaci['sifraPonude']>;
  brojPartije: FormControl<IPonudePonudjaci['brojPartije']>;
  nazivProizvodjaca: FormControl<IPonudePonudjaci['nazivProizvodjaca']>;
  nazivPonudjaca: FormControl<IPonudePonudjaci['nazivPonudjaca']>;
  zasticeniNaziv: FormControl<IPonudePonudjaci['zasticeniNaziv']>;
  ponudjenaVrijednost: FormControl<IPonudePonudjaci['ponudjenaVrijednost']>;
  rokIsporuke: FormControl<IPonudePonudjaci['rokIsporuke']>;
  jedinicnaCijena: FormControl<IPonudePonudjaci['jedinicnaCijena']>;
  selected: FormControl<IPonudePonudjaci['selected']>;
};

export type PonudePonudjaciFormGroup = FormGroup<PonudePonudjaciFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PonudePonudjaciFormService {
  createPonudePonudjaciFormGroup(ponudePonudjaci: PonudePonudjaciFormGroupInput = { id: null }): PonudePonudjaciFormGroup {
    const ponudePonudjaciRawValue = {
      ...this.getFormDefaults(),
      ...ponudePonudjaci,
    };
    return new FormGroup<PonudePonudjaciFormGroupContent>({
      id: new FormControl(
        { value: ponudePonudjaciRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sifraPostupka: new FormControl(ponudePonudjaciRawValue.sifraPostupka, {
        validators: [Validators.required],
      }),
      sifraPonude: new FormControl(ponudePonudjaciRawValue.sifraPonude, {
        validators: [Validators.required],
      }),
      brojPartije: new FormControl(ponudePonudjaciRawValue.brojPartije, {
        validators: [Validators.required],
      }),
      nazivProizvodjaca: new FormControl(ponudePonudjaciRawValue.nazivProizvodjaca),
      nazivPonudjaca: new FormControl(ponudePonudjaciRawValue.nazivPonudjaca),
      zasticeniNaziv: new FormControl(ponudePonudjaciRawValue.zasticeniNaziv),
      ponudjenaVrijednost: new FormControl(ponudePonudjaciRawValue.ponudjenaVrijednost, {
        validators: [Validators.required],
      }),
      rokIsporuke: new FormControl(ponudePonudjaciRawValue.rokIsporuke),
      jedinicnaCijena: new FormControl(ponudePonudjaciRawValue.jedinicnaCijena),
      selected: new FormControl(ponudePonudjaciRawValue.selected),
    });
  }

  getPonudePonudjaci(form: PonudePonudjaciFormGroup): IPonudePonudjaci | NewPonudePonudjaci {
    return form.getRawValue() as IPonudePonudjaci | NewPonudePonudjaci;
  }

  resetForm(form: PonudePonudjaciFormGroup, ponudePonudjaci: PonudePonudjaciFormGroupInput): void {
    const ponudePonudjaciRawValue = { ...this.getFormDefaults(), ...ponudePonudjaci };
    form.reset(
      {
        ...ponudePonudjaciRawValue,
        id: { value: ponudePonudjaciRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PonudePonudjaciFormDefaults {
    return {
      id: null,
      selected: false,
    };
  }
}
