import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PonudePonudjaciDetailComponent } from './ponude-ponudjaci-detail.component';

describe('PonudePonudjaci Management Detail Component', () => {
  let comp: PonudePonudjaciDetailComponent;
  let fixture: ComponentFixture<PonudePonudjaciDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PonudePonudjaciDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ponudePonudjaci: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PonudePonudjaciDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PonudePonudjaciDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ponudePonudjaci on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ponudePonudjaci).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
