import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonPartComponent } from './common-part.component';


const routes: Routes = [
  {
    path: '',
    component: CommonPartComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommonPartRoutingModule {}