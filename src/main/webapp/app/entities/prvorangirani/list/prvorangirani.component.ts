import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { IPrvorangirani } from '../prvorangirani.model';
import { PrvorangiraniService } from '../service/prvorangirani.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { IVrednovanje } from '../../vrednovanje/vrednovanje.model';

@Component({
  selector: 'jhi-prvorangirani',
  templateUrl: './prvorangirani.component.html',
  styleUrls: ['./prvorangirani.component.scss'],
})
export class PrvorangiraniComponent implements OnInit, AfterViewInit {
  prvorangiranis?: HttpResponse<IPrvorangirani[]>;
  isLoading = false;
  ukupno?: number;
  ukupnaProcijenjena?: number;
  ukupnoPonudjena?: number;
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
  ];

  public dataSource = new MatTableDataSource<IPrvorangirani>();
  sifraPostupka?: any;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @Input() postupak: any;
  constructor(protected prvorangiraniService: PrvorangiraniService, protected activatedRoute: ActivatedRoute, protected router: Router) {}

  loadPageSifraPostupka(): void {
    this.prvorangiraniService.queryPrvorangiraniPostupak(this.postupak).subscribe({
      next: (res: HttpResponse<IVrednovanje[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.prvorangiranis = res;
        this.ukupno = res.body?.reduce((acc, ponude) => acc + ponude.ponudjenaVrijednost!, 0);
      },
      error: () => {
        this.isLoading = false;
        this.onError();
      },
    });
  }

  loadPage(): void {
    this.isLoading = true;
    this.prvorangiraniService.queryNative().subscribe({
      next: (res: HttpResponse<IPrvorangirani[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.prvorangiranis = res;
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
}
