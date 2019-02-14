import { NgModule } from '@angular/core';

import { FuseSharedModule } from '@fuse/shared.module';

import { MatExpansionModule } from '@angular/material'
import { CommonModule } from '@angular/common';
import { ContactModule } from 'app/main/common-components/contact/contact.modul';
import { NpsComponent } from './nps.component';
import { CommonPartService } from '../common-part/common-part.service';
import { GroupModule } from 'app/main/common-components/group/group.component';

@NgModule({
    declarations: [
        NpsComponent,
    ],
    imports     : [
        CommonModule,               
        ContactModule,
        FuseSharedModule,
        GroupModule,
        MatExpansionModule,
    ],
    exports: [NpsComponent],
    providers: [CommonPartService]
})

export class NpsModule { }
