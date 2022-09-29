import { ISpecifikacije, NewSpecifikacije } from './specifikacije.model';

export const sampleWithRequiredData: ISpecifikacije = {
  id: 59987,
  sifraPostupka: 94554,
  brojPartije: 41925,
  procijenjenaVrijednost: 4724,
};

export const sampleWithPartialData: ISpecifikacije = {
  id: 98138,
  sifraPostupka: 10987,
  brojPartije: 43372,
  atc: 'content',
  inn: 'International',
  trazenaKolicina: 57602,
  pakovanje: 'haptic',
  jedinicaMjere: 'Zloty Account Account',
  procijenjenaVrijednost: 9520,
};

export const sampleWithFullData: ISpecifikacije = {
  id: 7483,
  sifraPostupka: 79985,
  brojPartije: 68360,
  atc: 'Cambridgeshire upward-trending Cotton',
  inn: 'Electronics compress',
  farmaceutskiOblikLijeka: 'Generic Granite Down-sized',
  jacinaLijeka: 'SQL',
  trazenaKolicina: 59394,
  pakovanje: 'EXE redundant',
  jedinicaMjere: 'Cheese',
  procijenjenaVrijednost: 39447,
};

export const sampleWithNewData: NewSpecifikacije = {
  sifraPostupka: 2139,
  brojPartije: 6576,
  procijenjenaVrijednost: 41486,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
