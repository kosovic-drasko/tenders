import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HvalePonudeComponent } from './list/hvale-ponude.component';
import { HvalePonudeRoutingModule } from './route/hvale-ponude-routing.module';
import { MatSortModule } from '@angular/material/sort';
import { MatSidenavModule } from '@angular/material/sidenav';
import { JhMaterialModule } from '../../shared/jh-material.module';

@NgModule({
  imports: [SharedModule, HvalePonudeRoutingModule, MatSortModule, MatSidenavModule, JhMaterialModule],
  declarations: [HvalePonudeComponent],
  exports: [HvalePonudeComponent],
})
export class HvalePonudeModule {}
