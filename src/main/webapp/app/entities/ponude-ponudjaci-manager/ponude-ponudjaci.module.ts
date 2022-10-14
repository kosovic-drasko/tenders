import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PonudePonudjaciComponentManager } from './list/ponude-ponudjaci.component-manager';
import { PonudePonudjaciDetailComponent } from './detail/ponude-ponudjaci-detail.component';
import { PonudePonudjaciRoutingModule } from './route/ponude-ponudjaci-routing.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { JhMaterialModule } from '../../shared/jh-material.module';
import { MatSortModule } from '@angular/material/sort';

@NgModule({
  imports: [SharedModule, PonudePonudjaciRoutingModule, MatSidenavModule, JhMaterialModule, MatSortModule],
  declarations: [PonudePonudjaciComponentManager, PonudePonudjaciDetailComponent],
  exports: [PonudePonudjaciComponentManager],
})
export class PonudePonudjaciModule {}
