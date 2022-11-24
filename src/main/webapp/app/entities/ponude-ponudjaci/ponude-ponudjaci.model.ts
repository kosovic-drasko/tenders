export interface IPonudePonudjaci {
  id: number;
  sifraPostupka?: number | null;
  sifraPonude?: number | null;
  brojPartije?: number | null;
  nazivProizvodjaca?: string | null;
  nazivPonudjaca?: string | null;
  sifraPonudjaca?: number | null;
  zasticeniNaziv?: string | null;
  ponudjenaVrijednost?: number | null;
  rokIsporuke?: number | null;
  jedinicnaCijena?: number | null;
  karakteristika?: string | null;
  selected?: boolean | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: string | null;
}

export type NewPonudePonudjaci = Omit<IPonudePonudjaci, 'id'> & { id: null };
