import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrvorangirani, getPrvorangiraniIdentifier } from '../prvorangirani.model';
import { IVrednovanje } from '../../vrednovanje/vrednovanje.model';

export type EntityResponseType = HttpResponse<IPrvorangirani>;
export type EntityArrayResponseType = HttpResponse<IPrvorangirani[]>;

@Injectable({ providedIn: 'root' })
export class PrvorangiraniService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prvorangiranis');
  protected resourceUrlNative = this.applicationConfigService.getEndpointFor('api/prvorangirani');
  protected resourceUrlPostupak = this.applicationConfigService.getEndpointFor('api/prvorangirani-postupci');
  protected resourceUrlPonude = this.applicationConfigService.getEndpointFor('api/prvorangirani-ponude');
  protected resourceUrlPonudjaciPostupci = this.applicationConfigService.getEndpointFor('api/prvorngirani-ponudjaci-postupci');
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrvorangirani>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrvorangirani[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryNative(): Observable<EntityArrayResponseType> {
    return this.http.get<IPrvorangirani[]>(this.resourceUrlNative, { observe: 'response' });
  }
  queryPrvorangiraniPostupak(sifraPostupka: number): Observable<EntityArrayResponseType> {
    return this.http.get<IPrvorangirani[]>(`${this.resourceUrlPostupak}/${sifraPostupka}`, { observe: 'response' });
  }

  queryPrvorangiraniPonude(sifraPonude: number | undefined): Observable<EntityArrayResponseType> {
    return this.http.get<IPrvorangirani[]>(`${this.resourceUrlPonude}/${sifraPonude}`, { observe: 'response' });
  }

  ponudePonudjaciPostupci(sifraPostupka: number): Observable<IPrvorangirani> {
    return this.http.get<IPrvorangirani>(`${this.resourceUrlPonudjaciPostupci}/${sifraPostupka}`);
  }
}
