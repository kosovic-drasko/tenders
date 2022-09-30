import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPonudePonudjaci } from '../ponude-ponudjaci.model';

@Component({
  selector: 'jhi-ponude-ponudjaci-detail',
  templateUrl: './ponude-ponudjaci-detail.component.html',
})
export class PonudePonudjaciDetailComponent implements OnInit {
  ponudePonudjaci: IPonudePonudjaci | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ponudePonudjaci }) => {
      this.ponudePonudjaci = ponudePonudjaci;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
