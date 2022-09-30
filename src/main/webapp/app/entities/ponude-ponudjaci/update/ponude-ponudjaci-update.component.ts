import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PonudePonudjaciFormService, PonudePonudjaciFormGroup } from './ponude-ponudjaci-form.service';
import { IPonudePonudjaci } from '../ponude-ponudjaci.model';
import { PonudePonudjaciService } from '../service/ponude-ponudjaci.service';

@Component({
  selector: 'jhi-ponude-ponudjaci-update',
  templateUrl: './ponude-ponudjaci-update.component.html',
})
export class PonudePonudjaciUpdateComponent implements OnInit {
  isSaving = false;
  ponudePonudjaci: IPonudePonudjaci | null = null;

  editForm: PonudePonudjaciFormGroup = this.ponudePonudjaciFormService.createPonudePonudjaciFormGroup();

  constructor(
    protected ponudePonudjaciService: PonudePonudjaciService,
    protected ponudePonudjaciFormService: PonudePonudjaciFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ponudePonudjaci }) => {
      this.ponudePonudjaci = ponudePonudjaci;
      if (ponudePonudjaci) {
        this.updateForm(ponudePonudjaci);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ponudePonudjaci = this.ponudePonudjaciFormService.getPonudePonudjaci(this.editForm);
    if (ponudePonudjaci.id !== null) {
      this.subscribeToSaveResponse(this.ponudePonudjaciService.update(ponudePonudjaci));
    } else {
      this.subscribeToSaveResponse(this.ponudePonudjaciService.create(ponudePonudjaci));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPonudePonudjaci>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(ponudePonudjaci: IPonudePonudjaci): void {
    this.ponudePonudjaci = ponudePonudjaci;
    this.ponudePonudjaciFormService.resetForm(this.editForm, ponudePonudjaci);
  }
}
