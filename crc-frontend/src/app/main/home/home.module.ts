import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

import { FuseSharedModule } from '@fuse/shared.module';

import { HomeComponent } from './home.component';
import { HomeRoutingModule } from './home-routing.module';
import { CommonModule } from '@angular/common';
import { SharedMaterialModule } from 'app/shared/shared-material.module';
import { OperatorService } from '../user/operator-administration/operator-dialog/operator.service';
import { ComapnyCardModule } from '../common-components/company-card/company-card.component';

@NgModule({
    declarations: [
        HomeComponent
    ],
    imports     : [
        HomeRoutingModule,
        TranslateModule,
        CommonModule,
        ComapnyCardModule,

        SharedMaterialModule,

        FuseSharedModule
    ],
    exports     : [
        HomeComponent
    ],
    providers: [OperatorService]
})

export class HomeModule
{
}
