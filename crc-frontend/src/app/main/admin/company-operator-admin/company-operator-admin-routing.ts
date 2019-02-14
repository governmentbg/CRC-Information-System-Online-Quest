import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompanyOperatorAdminComponent } from './company-operator-admin.component';


const routes: Routes = [
  {
    path: '',
    component: CompanyOperatorAdminComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyOperatorAdminRouting {}