import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CRCUserAdministrationComponent } from './crc-user-administration.component';


const routes: Routes = [
  {
    path: '',
    component: CRCUserAdministrationComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CRCUserAdministrationRoutingModule {}