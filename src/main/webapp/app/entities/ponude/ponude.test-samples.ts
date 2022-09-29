import { IPonude, NewPonude } from './ponude.model';

export const sampleWithRequiredData: IPonude = {
  id: 94075,
  sifraPostupka: 25624,
  sifraPonude: 43998,
  brojPartije: 80246,
  ponudjenaVrijednost: 18270,
};

export const sampleWithPartialData: IPonude = {
  id: 71596,
  sifraPostupka: 91419,
  sifraPonude: 80966,
  brojPartije: 97633,
  zasticeniNaziv: 'innovate syndicate',
  ponudjenaVrijednost: 37648,
  rokIsporuke: 56935,
  jedinicnaCijena: 99552,
  selected: true,
};

export const sampleWithFullData: IPonude = {
  id: 80575,
  sifraPostupka: 81047,
  sifraPonude: 73092,
  brojPartije: 41663,
  nazivProizvodjaca: 'Monitored Account Avenue',
  zasticeniNaziv: 'coherent Dinar',
  ponudjenaVrijednost: 25324,
  rokIsporuke: 94710,
  jedinicnaCijena: 44993,
  selected: true,
  sifraPonudjaca: 43728,
};

export const sampleWithNewData: NewPonude = {
  sifraPostupka: 82376,
  sifraPonude: 33646,
  brojPartije: 91164,
  ponudjenaVrijednost: 64395,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
