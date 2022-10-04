import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrvorangirani, getPrvorangiraniIdentifier } from '../prvorangirani.model';
import { IPonudePonudjaci } from '../../ponude-ponudjaci/ponude-ponudjaci.model';

export type EntityResponseType = HttpResponse<IPrvorangirani>;
export type EntityArrayResponseType = HttpResponse<IPrvorangirani[]>;

@Injectable({ providedIn: 'root' })
export class PrvorangiraniService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prvorangiranis');
  protected resourceUrlNative = this.applicationConfigService.getEndpointFor('api/prvorangirani');

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

  addPrvorangiraniToCollectionIfMissing(
    prvorangiraniCollection: IPrvorangirani[],
    ...prvorangiranisToCheck: (IPrvorangirani | null | undefined)[]
  ): IPrvorangirani[] {
    const prvorangiranis: IPrvorangirani[] = prvorangiranisToCheck.filter(isPresent);
    if (prvorangiranis.length > 0) {
      const prvorangiraniCollectionIdentifiers = prvorangiraniCollection.map(
        prvorangiraniItem => getPrvorangiraniIdentifier(prvorangiraniItem)!
      );
      const prvorangiranisToAdd = prvorangiranis.filter(prvorangiraniItem => {
        const prvorangiraniIdentifier = getPrvorangiraniIdentifier(prvorangiraniItem);
        if (prvorangiraniIdentifier == null || prvorangiraniCollectionIdentifiers.includes(prvorangiraniIdentifier)) {
          return false;
        }
        prvorangiraniCollectionIdentifiers.push(prvorangiraniIdentifier);
        return true;
      });
      return [...prvorangiranisToAdd, ...prvorangiraniCollection];
    }
    return prvorangiraniCollection;
  }
}
