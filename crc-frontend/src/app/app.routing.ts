import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoggedinGuard } from './guards/loggedin.guards';
import { AuthGuard } from './guards/auth.guard';
import { NgModule } from '@angular/core';
import { VerticalLayout1Component } from '../app/layout/vertical/layout-1/layout-1.component';
import { PostComponent } from './main/questionnaire/post/post.component';

export const APP_ROUTES: Routes = [

  { path: '', loadChildren: '../app/main/home/home.module#HomeModule', canActivate: [AuthGuard] },
  { path: 'home', loadChildren: '../app/main/home/home.module#HomeModule', canActivate: [AuthGuard] },
  { path: 'register', loadChildren: '../app/main/register/register.module#RegisterModule' },
  { path: 'login', loadChildren: '../app/main/login/login.module#LoginModule', pathMatch: 'full' },
  { path: 'reset-password', loadChildren: '../app/main/reset-password/reset-password.module#ResetPasswordModule' },
  { path: 'history', loadChildren: '../app/main/history/history.module#HistoryModule' },
  // { path: 'user', loadChildren: '../app/main/user/user.module#UserModule', canActivate: [AuthGuard] },
  { path: 'user/operator-administration', loadChildren: '../app/main/user/operator-administration/operator-administration.module#OperatorAdministrationModule', canActivate: [AuthGuard] },
  { path: 'user/crc-user-administration', loadChildren: '../app/main/user/crc-user-administration/crc-user-administration.module#CRCUserAdministrationModule', canActivate: [AuthGuard] },
  { path: 'admin/company-operator-admin', loadChildren: '../app/main/admin/company-operator-admin/company-operator-admin.module#CompanyOperatorAdminModule', canActivate: [AuthGuard] },
  {path: 'questionnaire', loadChildren: '../app/main/questionnaire/questionnaire.module#QuestionnaireModule', canActivate: [AuthGuard] },
  {path: 'questionnaire/post', loadChildren: '../app/main/questionnaire/post/post.module#PostModule', canActivate: [AuthGuard] },
  { path: 'error/error-404', loadChildren: '../app/main/errors/404/error-404.module#Error404Module' },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(APP_ROUTES, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
