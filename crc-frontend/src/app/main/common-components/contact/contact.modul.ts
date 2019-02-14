import { NgModule } from '@angular/core';

import { FuseSharedModule } from '@fuse/shared.module';

import { MatFormFieldModule, MatInputModule, MatRadioModule, MatTabsModule, MatTableModule, MatCheckboxModule } from '@angular/material'
import { CommonModule } from '@angular/common';
import { ContactComponent } from './contact.component';
import { SharedMaterialModule } from 'app/shared/shared-material.module';

@NgModule({
    declarations: [
        ContactComponent
    ],
    imports     : [
        CommonModule,

        SharedMaterialModule,

        FuseSharedModule
    ],
    exports     : [
        ContactComponent
    ],
})

export class ContactModule
{
}
