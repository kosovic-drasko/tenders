import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PonudePonudjaciComponent } from '../list/ponude-ponudjaci.component';
import { PonudePonudjaciDetailComponent } from '../detail/ponude-ponudjaci-detail.component';
import { PonudePonudjaciUpdateComponent } from '../update/ponude-ponudjaci-update.component';
import { PonudePonudjaciRoutingResolveService } from './ponude-ponudjaci-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ponudePonudjaciRoute: Routes = [
  {
    path: '',
    component: PonudePonudjaciComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PonudePonudjaciDetailComponent,
    resolve: {
      ponudePonudjaci: PonudePonudjaciRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PonudePonudjaciUpdateComponent,
    resolve: {
      ponudePonudjaci: PonudePonudjaciRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PonudePonudjaciUpdateComponent,
    resolve: {
      ponudePonudjaci: PonudePonudjaciRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ponudePonudjaciRoute)],
  exports: [RouterModule],
})
export class PonudePonudjaciRoutingModule {}
