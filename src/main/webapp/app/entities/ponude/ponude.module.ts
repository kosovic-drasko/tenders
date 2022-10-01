import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PonudeUpdateComponent } from './update/ponude-update.component';
import { PonudeDeleteDialogComponent } from './delete/ponude-delete-dialog.component';
import { PonudeRoutingModule } from './route/ponude-routing.module';
import { MatSortModule } from '@angular/material/sort';
import { MatSidenavModule } from '@angular/material/sidenav';
import { JhMaterialModule } from '../../shared/jh-material.module';

@NgModule({
  imports: [SharedModule, PonudeRoutingModule, MatSortModule, MatSidenavModule, JhMaterialModule],
  declarations: [PonudeUpdateComponent, PonudeDeleteDialogComponent],
  entryComponents: [PonudeDeleteDialogComponent],
  exports: [],
})
export class PonudeModule {}
