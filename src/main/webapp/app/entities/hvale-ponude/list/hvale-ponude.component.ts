import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { IHvalePonude } from '../hvale-ponude.model';
import { HvalePonudeService } from '../service/hvale-ponude.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ISpecifikacije } from '../../specifikacije/specifikacije.model';

@Component({
  selector: 'jhi-hvale-ponude',
  templateUrl: './hvale-ponude.component.html',
  styleUrls: ['./hvale-ponude.component.scss'],
})
export class HvalePonudeComponent implements AfterViewInit, OnChanges {
  hvalePonudes?: any;
  ukupnaProcijenjena?: number | null | undefined;
  isLoading = false;
  ukupno?: number;
  public displayedColumns = [
    'sifra postupka',
    'broj partije',
    'inn',
    'farmaceutski oblik',
    'pakovanje',
    'kolicina',
    'procijenjena vrijednost',
  ];

  public dataSource = new MatTableDataSource<IHvalePonude>();
  sifraPostupka?: number;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @Input() postupak: any;

  constructor(protected hvaleService: HvalePonudeService) {}

  public getSifraHvali(): void {
    this.hvaleService.hvali(this.postupak).subscribe((res: IHvalePonude[]) => {
      this.dataSource.data = res;
      this.hvalePonudes = res;
      this.getTotalProcijenjena();
      console.log('<<<<<<<<<<<<<<<<<<<', this.hvalePonudes);
    });
  }
  loadPageSifra(): void {
    this.isLoading = true;
    this.hvaleService.query(this.postupak).subscribe({
      next: (res: HttpResponse<IHvalePonude[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.hvalePonudes = res;
        this.ukupno = res.body?.reduce((acc, ponude) => acc + ponude.procijenjenaVrijednost!, 0);
        console.log('<<<<<<<<<<<<<<<<<<<', this.hvalePonudes);
      },
      error: () => {
        this.isLoading = false;
        this.onError();
      },
    });
  }
  protected onError(): void {
    console.log('Greska');
  }
  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  getTotalProcijenjena(): any {
    return (this.ukupnaProcijenjena = this.dataSource.filteredData
      .map(t => t.procijenjenaVrijednost)
      .reduce((acc, value) => acc! + value!, 0));
  }

  ngOnChanges(): void {
    this.loadPageSifra();
    // this.getSifraHvali();
  }
}
