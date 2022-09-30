import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PonudjaciComponent } from '../list/ponudjaci.component';
import { PonudjaciUpdateComponent } from '../update/ponudjaci-update.component';
import { PonudjaciRoutingResolveService } from './ponudjaci-routing-resolve.service';

const ponudjaciRoute: Routes = [
  {
    path: '',
    component: PonudjaciComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: 'new',
    component: PonudjaciUpdateComponent,
    resolve: {
      ponudjaci: PonudjaciRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PonudjaciUpdateComponent,
    resolve: {
      ponudjaci: PonudjaciRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ponudjaciRoute)],
  exports: [RouterModule],
})
export class PonudjaciRoutingModule {}
