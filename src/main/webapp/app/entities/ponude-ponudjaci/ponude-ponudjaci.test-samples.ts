import { IPonudePonudjaci, NewPonudePonudjaci } from './ponude-ponudjaci.model';

export const sampleWithRequiredData: IPonudePonudjaci = {
  id: 92284,
  sifraPostupka: 12919,
  sifraPonude: 31599,
  brojPartije: 87178,
  ponudjenaVrijednost: 94992,
};

export const sampleWithPartialData: IPonudePonudjaci = {
  id: 18870,
  sifraPostupka: 10977,
  sifraPonude: 39281,
  brojPartije: 76646,
  ponudjenaVrijednost: 76621,
  selected: true,
};

export const sampleWithFullData: IPonudePonudjaci = {
  id: 55212,
  sifraPostupka: 54444,
  sifraPonude: 37389,
  brojPartije: 48213,
  nazivProizvodjaca: 'Enterprise-wide Island',
  nazivPonudjaca: 'magenta',
  zasticeniNaziv: 'Handmade Kids',
  ponudjenaVrijednost: 73209,
  rokIsporuke: 22662,
  jedinicnaCijena: 84806,
  selected: false,
};

export const sampleWithNewData: NewPonudePonudjaci = {
  sifraPostupka: 93340,
  sifraPonude: 43923,
  brojPartije: 25619,
  ponudjenaVrijednost: 84553,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
