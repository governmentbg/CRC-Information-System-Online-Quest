import { NgModule } from '@angular/core';

import { FuseSharedModule } from '@fuse/shared.module';

import { MatExpansionModule } from '@angular/material'
import { CommonModule } from '@angular/common';
import { ContactModule } from 'app/main/common-components/contact/contact.modul';
import { UpuComponent } from './upu.component';
import { CommonPartService } from '../common-part/common-part.service';
import { GroupModule } from 'app/main/common-components/group/group.component';

@NgModule({
    declarations: [
        UpuComponent,
    ],
    imports     : [
        CommonModule,               
        ContactModule,
        FuseSharedModule,
        GroupModule,
        MatExpansionModule,
    ],
    exports: [UpuComponent],
    providers: [CommonPartService]
})

export class UpuModule { }
