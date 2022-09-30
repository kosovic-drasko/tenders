import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PonudePonudjaciService } from '../service/ponude-ponudjaci.service';

import { PonudePonudjaciComponent } from './ponude-ponudjaci.component';

describe('PonudePonudjaci Management Component', () => {
  let comp: PonudePonudjaciComponent;
  let fixture: ComponentFixture<PonudePonudjaciComponent>;
  let service: PonudePonudjaciService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'ponude-ponudjaci', component: PonudePonudjaciComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PonudePonudjaciComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PonudePonudjaciComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PonudePonudjaciComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PonudePonudjaciService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.ponudePonudjacis?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to ponudePonudjaciService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPonudePonudjaciIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPonudePonudjaciIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
