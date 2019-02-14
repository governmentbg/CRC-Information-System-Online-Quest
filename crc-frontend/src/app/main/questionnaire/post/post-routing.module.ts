import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PostComponent } from './post.component';
import { AuthGuard } from 'app/guards/auth.guard';


const routes: Routes = [
  {
    path: '',
    component: PostComponent,
    // children: [ 
    //   { 
    //     path: 'common-part',
    //     outlet: 'aside', 
    //     loadChildren: '../app/main/questionnaire/post/common-part/common-part.module#CommonPartModule',
    //     canActivate: [AuthGuard] 
    //   }],

  }
  // {
  //   path: 'common-part',
  //   outlet: 'aside', 
  //   loadChildren: '../app/main/questionnaire/post/common-part/common-part.module#CommonPartModule',
  //   canActivate: [AuthGuard] 
  // }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostRoutingModule {}