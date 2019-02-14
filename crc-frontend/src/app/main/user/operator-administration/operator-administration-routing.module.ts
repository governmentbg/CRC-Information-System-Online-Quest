import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OperatorAdministrationComponent } from './operator-administration.component';


const routes: Routes = [
  {
    path: '',
    component: OperatorAdministrationComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperatorAdministrationUserRoutingModule {}