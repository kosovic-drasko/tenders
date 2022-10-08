import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { IVrednovanje } from '../vrednovanje.model';
import { VrednovanjeService } from '../service/vrednovanje.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { TableUtil } from '../tableUtil';

@Component({
  selector: 'jhi-vrednovanje',
  templateUrl: './vrednovanje.component.html',
  styleUrls: ['./vrednovanje.scss'],
})
export class VrednovanjeComponent implements AfterViewInit, OnInit {
  vrednovanjes?: HttpResponse<IVrednovanje[]>;
  isLoading = false;
  ukupno?: number;
  brPonude?: any;
  ponudjaciPostupak?: any;
  ukupnaProcijenjena?: number;
  ukupnoPonudjena?: number;
  sifraPonude?: number;
  public displayedColumns = [
    'sifra postupka',
    'sifra ponude',
    'broj partije',
    'atc',
    'zasticeni naziv',
    'procijenjena vrijednost',
    'kolicina',
    'ponudjena vrijednost',
    'rok isporuke',
    'naziv ponudjaca',
    'naziv proizvodjaca',
    'bod cijena',
    'bod rok',
    'bod ukupno',
  ];
  public dataSource = new MatTableDataSource<IVrednovanje>();
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @Input() postupak: any;
  // @ViewChild(MatTableExporterDirective) exporter: MatTableExporterDirective ;
  constructor(protected vrednovanjeService: VrednovanjeService, protected activatedRoute: ActivatedRoute, protected router: Router) {}

  loadPage(): void {
    this.isLoading = true;
    this.vrednovanjeService.queryNative().subscribe({
      next: (res: HttpResponse<IVrednovanje[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.vrednovanjes = res;
        this.ukupnoPonudjena = res.body?.reduce((acc, ponude) => acc + ponude.ponudjenaVrijednost!, 0);
        this.ukupnaProcijenjena = res.body?.reduce((acc, ponude) => acc + ponude.procijenjenaVrijednost!, 0);
      },
      error: () => {
        this.isLoading = false;
        this.onError();
      },
    });
  }
  loadPonudePonudjaci(sifraPostupka: number): void {
    this.vrednovanjeService.ponudePonudjaciPostupci(sifraPostupka).subscribe({
      next: res => {
        this.ponudjaciPostupak = res;
      },
    });
  }
  loadPageSifraPostupka(): void {
    this.vrednovanjeService.queryVrednovanjePostupak(this.postupak).subscribe({
      next: (res: HttpResponse<IVrednovanje[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.vrednovanjes = res;
        this.ukupnoPonudjena = res.body?.reduce((acc, ponude) => acc + ponude.ponudjenaVrijednost!, 0);
        this.ukupnaProcijenjena = res.body?.reduce((acc, ponude) => acc + ponude.procijenjenaVrijednost!, 0);
      },
      error: () => {
        this.isLoading = false;
        this.onError();
      },
    });
  }
  loadPageSifraPonude(): void {
    this.vrednovanjeService.queryVrednovanjePonude(this.sifraPonude).subscribe({
      next: (res: HttpResponse<IVrednovanje[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.vrednovanjes = res;
        this.ukupnoPonudjena = res.body?.reduce((acc, ponude) => acc + ponude.ponudjenaVrijednost!, 0);
        this.ukupnaProcijenjena = res.body?.reduce((acc, ponude) => acc + ponude.procijenjenaVrijednost!, 0);
      },
      error: () => {
        this.isLoading = false;
        this.onError();
      },
    });
  }
  ngOnInit(): void {
    if (this.postupak !== undefined) {
      this.loadPageSifraPostupka();
    } else {
      this.loadPage();
    }
  }

  protected onError(): void {
    console.log('Greska');
  }
  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  exportTable() {
    TableUtil.exportTableToExcel('ExampleTable');
  }
}
