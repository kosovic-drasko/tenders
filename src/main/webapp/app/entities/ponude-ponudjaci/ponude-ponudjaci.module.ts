import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PonudePonudjaciComponent } from './list/ponude-ponudjaci.component';
import { PonudePonudjaciDetailComponent } from './detail/ponude-ponudjaci-detail.component';
import { PonudePonudjaciUpdateComponent } from './update/ponude-ponudjaci-update.component';
import { PonudePonudjaciDeleteDialogComponent } from './delete/ponude-ponudjaci-delete-dialog.component';
import { PonudePonudjaciRoutingModule } from './route/ponude-ponudjaci-routing.module';

@NgModule({
  imports: [SharedModule, PonudePonudjaciRoutingModule],
  declarations: [
    PonudePonudjaciComponent,
    PonudePonudjaciDetailComponent,
    PonudePonudjaciUpdateComponent,
    PonudePonudjaciDeleteDialogComponent,
  ],
})
export class PonudePonudjaciModule {}
