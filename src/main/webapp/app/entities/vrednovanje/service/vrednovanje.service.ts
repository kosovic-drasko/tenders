import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVrednovanje } from '../vrednovanje.model';

export type EntityResponseType = HttpResponse<IVrednovanje>;
export type EntityArrayResponseType = HttpResponse<IVrednovanje[]>;

@Injectable({ providedIn: 'root' })
export class VrednovanjeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vrednovanjes');

  protected resourceUrlNative = this.applicationConfigService.getEndpointFor('api/vrednovanje');
  protected resourceUrlPostupak = this.applicationConfigService.getEndpointFor('api/vrednovanje-postupak');
  protected resourceUrlPonude = this.applicationConfigService.getEndpointFor('api/vrednovanje-ponude');
  protected resourceUrlPonudjaciPostupci = this.applicationConfigService.getEndpointFor('api/vrednovanje-ponudjaci-postupci');
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVrednovanje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVrednovanje[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryNative(): Observable<EntityArrayResponseType> {
    return this.http.get<IVrednovanje[]>(this.resourceUrlNative, { observe: 'response' });
  }
  queryVrednovanjePostupak(sifraPostupka: number): Observable<EntityArrayResponseType> {
    return this.http.get<IVrednovanje[]>(`${this.resourceUrlPostupak}/${sifraPostupka}`, { observe: 'response' });
  }

  queryVrednovanjePonude(sifraPonude: null | undefined): Observable<EntityArrayResponseType> {
    return this.http.get<IVrednovanje[]>(`${this.resourceUrlPonude}/${sifraPonude}`, { observe: 'response' });
  }

  ponudePonudjaciPostupci(sifraPostupka: number): Observable<IVrednovanje> {
    return this.http.get<IVrednovanje>(`${this.resourceUrlPonudjaciPostupci}/${sifraPostupka}`);
  }
}
