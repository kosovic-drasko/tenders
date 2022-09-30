export interface IPonudePonudjaci {
  id: number;
  sifraPostupka?: number | null;
  sifraPonude?: number | null;
  brojPartije?: number | null;
  nazivProizvodjaca?: string | null;
  nazivPonudjaca?: string | null;
  zasticeniNaziv?: string | null;
  ponudjenaVrijednost?: number | null;
  rokIsporuke?: number | null;
  jedinicnaCijena?: number | null;
  selected?: boolean | null;
}

export type NewPonudePonudjaci = Omit<IPonudePonudjaci, 'id'> & { id: null };
