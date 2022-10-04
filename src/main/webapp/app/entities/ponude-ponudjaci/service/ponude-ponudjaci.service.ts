import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPonudePonudjaci, NewPonudePonudjaci } from '../ponude-ponudjaci.model';

export type PartialUpdatePonudePonudjaci = Partial<IPonudePonudjaci> & Pick<IPonudePonudjaci, 'id'>;

export type EntityResponseType = HttpResponse<IPonudePonudjaci>;
export type EntityArrayResponseType = HttpResponse<IPonudePonudjaci[]>;

@Injectable({ providedIn: 'root' })
export class PonudePonudjaciService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ponude-ponudjacis');
  protected resourceUrlPostupak = this.applicationConfigService.getEndpointFor('api/ponude-ponudjaci-postupak');
  protected resourceUrlNative = this.applicationConfigService.getEndpointFor('api/ponude-ponudjaci');
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPonudePonudjaci>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPonudePonudjaci[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryNative(): Observable<EntityArrayResponseType> {
    return this.http.get<IPonudePonudjaci[]>(this.resourceUrlNative, { observe: 'response' });
  }
  queryPonudePonudjaciPostupak(sifraPostupka: number): Observable<EntityArrayResponseType> {
    return this.http.get<IPonudePonudjaci[]>(`${this.resourceUrlPostupak}/${sifraPostupka}`, { observe: 'response' });
  }
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
