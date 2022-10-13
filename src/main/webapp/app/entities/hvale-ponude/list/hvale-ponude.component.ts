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

  loadPageSifra(): void {
    this.isLoading = true;
    this.hvaleService.query(this.postupak).subscribe({
      next: (res: HttpResponse<IHvalePonude[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.hvalePonudes = res;
        this.ukupnaProcijenjena = res.body?.reduce((acc, ponude) => acc + ponude.procijenjenaVrijednost!, 0);
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
  ngOnChanges(): void {
    this.loadPageSifra();
  }
}
