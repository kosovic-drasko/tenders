import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPonudePonudjaci } from '../ponude-ponudjaci.model';
import { PonudePonudjaciService } from '../service/ponude-ponudjaci.service';

@Injectable({ providedIn: 'root' })
export class PonudePonudjaciRoutingResolveService implements Resolve<IPonudePonudjaci | null> {
  constructor(protected service: PonudePonudjaciService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPonudePonudjaci | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ponudePonudjaci: HttpResponse<IPonudePonudjaci>) => {
          if (ponudePonudjaci.body) {
            return of(ponudePonudjaci.body);
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
