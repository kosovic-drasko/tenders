import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPonudePonudjaci } from '../ponude-ponudjaci.model';
import { PonudePonudjaciService } from '../service/ponude-ponudjaci.service';
import { SortService } from 'app/shared/sort/sort.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { HttpResponse } from '@angular/common/http';
import { AccountService } from '../../../core/auth/account.service';
import { Account } from '../../../core/auth/account.model';
import { IPonudjaci } from '../../ponudjaci/ponudjaci.model';
import { PonudeDeleteDialogComponent } from '../../ponude/delete/ponude-delete-dialog.component';
import { PonudeService } from '../../ponude/service/ponude.service';
import { IPonude } from '../../ponude/ponude.model';
import { PonudeUpdateComponent } from '../../ponude/update/ponude-update.component';
import { TableUtil } from '../../tableUtil';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { IVrednovanje } from '../../vrednovanje/vrednovanje.model';

@Component({
  selector: 'jhi-ponude-ponudjaci',
  templateUrl: './ponude-ponudjaci.component.html',
  styleUrls: ['./ponude-ponudjaci.component.scss'],
})
export class PonudePonudjaciComponent implements OnInit {
  ponudePonudjacis?: HttpResponse<IPonudePonudjaci[]>;
  isLoading = false;
  ponude?: HttpResponse<IPonude[]>;
  ponudjaciPostupak?: any;
  currentAccount: Account | null = null;
  ponudjaci?: IPonudePonudjaci[] = [];
  ukupno?: number;
  brojObrazac?: number = 0;
  sifraPonude?: any;
  obrisanoSelektovano?: boolean = false;
  obrisanoSifraPonude?: boolean = false;
  sakrij?: boolean = true;

  private readonly destroy$ = new Subject<void>();
  public displayedColumns = [
    'sifra postupka',
    'sifraPonude',
    'brojPartije',
    'naziv proizvodjaca',
    'ponudjac',
    'zasticeni naziv',
    'karakteristika',
    'ponudjena vrijednost',
    'jedinicna cijena',
    'rok isporuke',
    'kreirao',
    'datum kreiranja',
    'zadnji izmjenio',
    'datum izmjene',
    'selected',
    'action',
  ];
  public dataSource: MatTableDataSource<IPonudePonudjaci> = new MatTableDataSource<IPonudePonudjaci>();
  @ViewChild('fileInput') fileInput: any;
  @Input() postupak: any;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public resourceUrlExcelDownloadPostupak = SERVER_API_URL + 'api/ponude/file';

  constructor(
    protected ponudePonudjaciService: PonudePonudjaciService,
    protected ponudeService: PonudeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {}
  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
  loadPage(): void {
    this.isLoading = true;
    this.ponudePonudjaciService.queryNative().subscribe({
      next: (res: HttpResponse<IPonudePonudjaci[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.ponudePonudjacis = res;
        this.ukupno = res.body?.reduce((acc, ponude) => acc + ponude.ponudjenaVrijednost!, 0);
        this.sifraPonude = null;
      },
      error: () => {
        this.isLoading = false;
        this.onError();
      },
    });
  }
  loadPageSifraPostupka(): void {
    this.ponudePonudjaciService
      .query({
        'sifraPostupka.in': this.postupak,
      })
      .subscribe({
        next: (res: HttpResponse<IPonudePonudjaci[]>) => {
          this.isLoading = false;
          this.dataSource.data = res.body ?? [];
          this.ponudjaci = res.body ?? [];
          console.log('====================>', this.ponudjaci);
          this.ukupno = res.body?.reduce((acc, ponude) => acc + ponude.ponudjenaVrijednost!, 0);
          this.sifraPonude = null;
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }
  ponisti(): void {
    if (this.postupak !== undefined) {
      this.sifraPonude = null;
      this.loadPageSifraPostupka();
      console.log(this.postupak);
    } else {
      this.sifraPonude = null;

      this.loadPage();
    }
  }

  nadji(): void {
    if (this.postupak !== undefined) {
      this.loadPageSifraPonude();
    } else {
      this.loadPageSifraPonude();
    }
  }

  // loadPonudePonudjaci(sifraPostupka: number): void {
  //   this.ponudePonudjaciService.ponudePonudjaciPostupci(sifraPostupka).subscribe({
  //     next: res => {
  //       this.ponudjaciPostupak = res;
  //       this.sifraPonude = null;
  //     },
  //   });
  // }

  loadPageSifraPonude(): void {
    this.isLoading = true;
    this.ponudePonudjaciService.query({ 'sifraPonude.in': this.sifraPonude }).subscribe({
      next: (res: HttpResponse<IPonudePonudjaci[]>) => {
        this.isLoading = false;
        this.dataSource.data = res.body ?? [];
        this.ponudePonudjacis = res;
        this.ukupno = res.body?.reduce((acc, ponudes) => acc + ponudes.ponudjenaVrijednost!, 0);
      },
      error: () => {
        this.isLoading = false;
        this.onError();
      },
    });
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    if (this.postupak !== undefined) {
      this.loadPageSifraPostupka();
    } else {
      this.loadPage();
    }
    if (this.currentAccount?.login == 'sandra') {
      this.sakrij = false;
    } else {
      this.sakrij = true;
    }
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.currentAccount = account));
    console.log('Nalog je >>>>>>>>', this.currentAccount?.authorities);
  }
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  delete(ponude: IPonude): void {
    const modalRef = this.modalService.open(PonudeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ponude = ponude;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  update(
    id?: number,
    sifraPostupka?: number,
    sifraPonude?: number,
    brojPartije?: number,
    sifraPonudjaca?: number | null,
    nazivProizvodjaca?: string | null,
    zasticeniNaziv?: string | null,
    karakteristike?: string | null,
    ponudjenaVrijednost?: number,
    jedinicnaCijena?: number | null,
    selected?: boolean | null,
    rokIsporuke?: number
  ): void {
    const modalRef = this.modalService.open(PonudeUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.sifraPostupka = sifraPostupka;
    modalRef.componentInstance.sifraPonude = sifraPonude;
    modalRef.componentInstance.brojPartije = brojPartije;
    modalRef.componentInstance.sifraPonudjaca = sifraPonudjaca;
    modalRef.componentInstance.nazivProizvodjaca = nazivProizvodjaca;
    modalRef.componentInstance.zasticeniNaziv = zasticeniNaziv;
    modalRef.componentInstance.karakteristike = karakteristike;
    modalRef.componentInstance.ponudjenaVrijednost = ponudjenaVrijednost;
    modalRef.componentInstance.jedinicnaCijena = jedinicnaCijena;
    modalRef.componentInstance.selected = selected;
    modalRef.componentInstance.rokIsporuke = rokIsporuke;

    modalRef.closed.subscribe(() => {
      if (this.postupak !== undefined) {
        this.loadPageSifraPostupka();
      } else {
        this.loadPage();
      }
    });
  }

  add(): void {
    const modalRef = this.modalService.open(PonudeUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  obrazacExcel(): void {
    window.location.href = `${this.resourceUrlExcelDownloadPostupak}/${this.brojObrazac}`;
  }
  uploadFile(): any {
    const formData = new FormData();
    formData.append('files', this.fileInput.nativeElement.files[0]);
    this.ponudeService.UploadExcel(formData).subscribe(() => {
      this.loadPage();
    });
  }

  obrazacExcelPostupak(): void {
    window.location.href = `${this.resourceUrlExcelDownloadPostupak}/${this.postupak}`;
  }

  deleteSifra(): void {
    this.ponudeService.deleteSifraPonude(this.sifraPonude).subscribe();
    if (this.postupak !== undefined) {
      setTimeout(() => {
        this.loadPageSifraPostupka();
      }, 500);
    } else {
      setTimeout(() => {
        this.loadPage();
      }, 500);
    }
    this.obrisanoSifraPonude = true;
    setTimeout(() => {
      this.obrisanoSifraPonude = false;
    }, 5000);
  }

  deleteSelected(): void {
    this.ponudeService.deleteSelected();
    if (this.postupak !== undefined) {
      setTimeout(() => {
        this.loadPageSifraPostupka();
      }, 500);
    } else {
      setTimeout(() => {
        this.loadPage();
      }, 500);
    }
    this.obrisanoSelektovano = true;
    setTimeout(() => {
      this.obrisanoSelektovano = false;
    }, 5000);
  }

  openBrisiSelektovano(contentBrisiSelect: any): any {
    this.modalService.open(contentBrisiSelect, { ariaLabelledBy: 'modal-basic-title' });
  }

  openBrisiPonudu(contentBrisiPoSifriPonude: any): any {
    this.modalService.open(contentBrisiPoSifriPonude, { ariaLabelledBy: 'modal-basic-title' });
  }
  updateSelected(id: number): any {
    this.ponudeService.updateSelected(id);
  }
  protected onError(): void {
    console.log('Greska');
  }

  exportTable() {
    TableUtil.exportTableToExcel('ExampleTable');
  }
  exportArray() {
    const onlyNameAndSymbolArr: {
      'naziv proizvodjaca': string | null | undefined;
      'jedinicna cijena': number | null | undefined;
      'ponudjana vrijednost': number | null | undefined;
      'rok isporuke': number | null | undefined;
      'naziv ponudjaca': string | null | undefined;
      'sifra ponude': number | null | undefined;
      'sifra postupka': number | null | undefined;
      'zasticeni naziv': string | null | undefined;
      'broj partije': number | null | undefined;
    }[] = this.dataSource.data.map(x => ({
      'sifra postupka': x.sifraPostupka,
      'broj partije': x.brojPartije,
      'sifra ponude': x.sifraPonude,
      'zasticeni naziv': x.zasticeniNaziv,
      'naziv proizvodjaca': x.nazivProizvodjaca,
      'naziv ponudjaca': x.nazivPonudjaca,
      'ponudjana vrijednost': x.ponudjenaVrijednost,
      'jedinicna cijena': x.jedinicnaCijena,
      'rok isporuke': x.rokIsporuke,
      'karakteristike ponude': x.karakteristika,
    }));
    TableUtil.exportArrayToExcel(onlyNameAndSymbolArr, 'Ponude');
  }

  // exportArray() {
  //   const onlyNameAndSymbolArr: Partial<ISpecifikacije>[] = this.dataSource.data.map(x => ({
  //     'sifra postupka':x.sifraPostupka,
  //     'broj_partije':x.brojPartije,
  //     'atc': x.atc,
  //     'inn': x.inn,
  //     'farmaceutski oblik':x.farmaceutskiOblikLijeka,
  //     'karakteristika':x.karakteristika,
  //     'jacina lijeka':x.jacinaLijeka,
  //     'trazena kolicina':x.trazenaKolicina,
  //     'pakovanje':x.pakovanje,
  //     'jedinica mjere':x.jedinicaMjere,
  //     'procijenjena vrijednost':x.jedinicaMjere,
  //     'jedinicna cijena':x.jedinicnaCijena
  //
  //   }));
  //   TableUtil.exportArrayToExcel(onlyNameAndSymbolArr, 'Specifikacija');
  // }

  konzola() {
    console.log('proba koinzola');
  }
}
