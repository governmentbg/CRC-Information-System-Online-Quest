import { NgModule } from '@angular/core';

import { FuseSharedModule } from '@fuse/shared.module';

import { MatFormFieldModule, MatInputModule, MatRadioModule, MatTabsModule, MatTableModule, MatCheckboxModule } from '@angular/material'
import { CommonModule } from '@angular/common';
import { AddressComponent } from './address.component';
import { SharedMaterialModule } from 'app/shared/shared-material.module';


@NgModule({
    declarations: [
        AddressComponent
    ],
    imports     : [
        CommonModule,
  
        SharedMaterialModule,

        FuseSharedModule
    ],
    exports     : [
        AddressComponent
    ],
})

export class AddressModule
{
}
