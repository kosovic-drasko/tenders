import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PonudePonudjaciComponent } from './list/ponude-ponudjaci.component';
import { PonudePonudjaciDetailComponent } from './detail/ponude-ponudjaci-detail.component';
import { PonudePonudjaciRoutingModule } from './route/ponude-ponudjaci-routing.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { JhMaterialModule } from '../../shared/jh-material.module';
import { MatSortModule } from '@angular/material/sort';

@NgModule({
  imports: [SharedModule, PonudePonudjaciRoutingModule, MatSidenavModule, JhMaterialModule, MatSortModule],
  declarations: [PonudePonudjaciComponent, PonudePonudjaciDetailComponent],
  exports: [PonudePonudjaciComponent],
})
export class PonudePonudjaciModule {}
