import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IViewPonude } from '../view-ponude.model';
import { ViewPonudeService } from '../service/view-ponude.service';

@Injectable({ providedIn: 'root' })
export class ViewPonudeRoutingResolveService implements Resolve<IViewPonude | null> {
  constructor(protected service: ViewPonudeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IViewPonude | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((viewPonude: HttpResponse<IViewPonude>) => {
          if (viewPonude.body) {
            return of(viewPonude.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
