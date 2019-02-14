import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { FuseSharedModule } from '@fuse/shared.module';

import { ResetPasswordComponent } from 'app/main/reset-password/reset-password.component';
import { ResetPasswordRoutingModule } from '../reset-password/reset-password-routing.module';


@NgModule({
    declarations: [
        ResetPasswordComponent
    ],
    imports     : [
        ResetPasswordRoutingModule,

        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,

        FuseSharedModule
    ]
})
export class ResetPasswordModule
{
}
