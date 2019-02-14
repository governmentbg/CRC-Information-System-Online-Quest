import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material';

import { FuseSharedModule } from '@fuse/shared.module';

import { Error404Component } from '../404/error-404.component';
import { Error404Routing } from './error-404-routing.component';


@NgModule({
    declarations: [
        Error404Component
    ],
    imports     : [
        Error404Routing,

        MatIconModule,

        FuseSharedModule
    ]
})
export class Error404Module
{
}
