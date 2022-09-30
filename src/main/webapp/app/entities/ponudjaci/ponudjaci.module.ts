import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PonudjaciComponent } from './list/ponudjaci.component';

import { PonudjaciUpdateComponent } from './update/ponudjaci-update.component';
import { PonudjaciDeleteDialogComponent } from './delete/ponudjaci-delete-dialog.component';
import { PonudjaciRoutingModule } from './route/ponudjaci-routing.module';
import { MatSortModule } from '@angular/material/sort';
import { JhMaterialModule } from '../../shared/jh-material.module';

@NgModule({
  imports: [SharedModule, PonudjaciRoutingModule, MatSortModule, JhMaterialModule],
  declarations: [PonudjaciComponent, PonudjaciUpdateComponent, PonudjaciDeleteDialogComponent],
  entryComponents: [PonudjaciDeleteDialogComponent],
  exports: [PonudjaciComponent],
})
export class PonudjaciModule {}
