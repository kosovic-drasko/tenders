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
  protected resourceUrlNative = this.applicationConfigService.getEndpointFor('api/ponude-ponudjaci');
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ponudePonudjaci: NewPonudePonudjaci): Observable<EntityResponseType> {
    return this.http.post<IPonudePonudjaci>(this.resourceUrl, ponudePonudjaci, { observe: 'response' });
  }

  update(ponudePonudjaci: IPonudePonudjaci): Observable<EntityResponseType> {
    return this.http.put<IPonudePonudjaci>(`${this.resourceUrl}/${this.getPonudePonudjaciIdentifier(ponudePonudjaci)}`, ponudePonudjaci, {
      observe: 'response',
    });
  }

  partialUpdate(ponudePonudjaci: PartialUpdatePonudePonudjaci): Observable<EntityResponseType> {
    return this.http.patch<IPonudePonudjaci>(`${this.resourceUrl}/${this.getPonudePonudjaciIdentifier(ponudePonudjaci)}`, ponudePonudjaci, {
      observe: 'response',
    });
  }

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
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPonudePonudjaciIdentifier(ponudePonudjaci: Pick<IPonudePonudjaci, 'id'>): number {
    return ponudePonudjaci.id;
  }

  comparePonudePonudjaci(o1: Pick<IPonudePonudjaci, 'id'> | null, o2: Pick<IPonudePonudjaci, 'id'> | null): boolean {
    return o1 && o2 ? this.getPonudePonudjaciIdentifier(o1) === this.getPonudePonudjaciIdentifier(o2) : o1 === o2;
  }

  addPonudePonudjaciToCollectionIfMissing<Type extends Pick<IPonudePonudjaci, 'id'>>(
    ponudePonudjaciCollection: Type[],
    ...ponudePonudjacisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ponudePonudjacis: Type[] = ponudePonudjacisToCheck.filter(isPresent);
    if (ponudePonudjacis.length > 0) {
      const ponudePonudjaciCollectionIdentifiers = ponudePonudjaciCollection.map(
        ponudePonudjaciItem => this.getPonudePonudjaciIdentifier(ponudePonudjaciItem)!
      );
      const ponudePonudjacisToAdd = ponudePonudjacis.filter(ponudePonudjaciItem => {
        const ponudePonudjaciIdentifier = this.getPonudePonudjaciIdentifier(ponudePonudjaciItem);
        if (ponudePonudjaciCollectionIdentifiers.includes(ponudePonudjaciIdentifier)) {
          return false;
        }
        ponudePonudjaciCollectionIdentifiers.push(ponudePonudjaciIdentifier);
        return true;
      });
      return [...ponudePonudjacisToAdd, ...ponudePonudjaciCollection];
    }
    return ponudePonudjaciCollection;
  }
}
