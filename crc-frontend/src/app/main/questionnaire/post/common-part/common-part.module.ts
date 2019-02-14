import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

import { FuseSharedModule } from '@fuse/shared.module';

import { CommonModule } from '@angular/common';
import { CommonPartComponent } from './common-part.component';
import { CommonPartRoutingModule } from './common-part-routing.module';
import { CommonPartService } from './common-part.service';
import { IdentityDataComponent } from './identity-data/identity-data.component';
import { RepresentativeOperatorComponent } from './representative-operator/representative-operator.component';
import { AddressModule } from 'app/main/common-components/address/address.module';
import { ContactModule } from 'app/main/common-components/contact/contact.modul';
import { SectionA2Component } from './section-a2/section-a2.component';
import { SectionA6Component } from './section-a6/section-a6.component';
import { SectionA7Component } from './section-a7/section-a7.component';
import { SharedMaterialModule } from 'app/shared/shared-material.module';

@NgModule({
    declarations: [
        CommonPartComponent,
        IdentityDataComponent,
        RepresentativeOperatorComponent,
        SectionA2Component,
        SectionA6Component,
        SectionA7Component
    ],
    imports     : [
        CommonPartRoutingModule,
        TranslateModule,
        CommonModule,
     
        SharedMaterialModule,

        AddressModule,
        ContactModule,
        FuseSharedModule
    ],
    exports     : [
        CommonPartComponent
    ],
    providers: [CommonPartService]
})

export class CommonPartModule
{
}
