import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PonudePonudjaciComponentManager } from '../list/ponude-ponudjaci.component-manager';
import { PonudePonudjaciDetailComponent } from '../detail/ponude-ponudjaci-detail.component';
import { PonudePonudjaciRoutingResolveService } from './ponude-ponudjaci-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ponudePonudjaciRoute: Routes = [
  {
    path: '',
    component: PonudePonudjaciComponentManager,
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
];

@NgModule({
  imports: [RouterModule.forChild(ponudePonudjaciRoute)],
  exports: [RouterModule],
})
export class PonudePonudjaciRoutingModule {}
