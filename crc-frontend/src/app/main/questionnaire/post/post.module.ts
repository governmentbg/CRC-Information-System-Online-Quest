import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

import { FuseSharedModule } from '@fuse/shared.module';

import { CommonModule } from '@angular/common';
import { PostComponent } from './post.component';
import { PostRoutingModule } from './post-routing.module';
import { CommonPartModule } from './common-part/common-part.module';
import { UpuModule } from './upu/upu.module';
import { SharedMaterialModule } from 'app/shared/shared-material.module';
import { AppUtilService } from 'app/main/util/app.util.service';
import { NpsModule } from './nps/nps.module';
import { PppModule } from './ppp/ppp.module';

@NgModule({
    declarations: [
        PostComponent,
    ],
    imports     : [
        PostRoutingModule,
        TranslateModule,
        CommonModule,
        
        SharedMaterialModule,

        CommonPartModule,
        UpuModule,
        NpsModule,
        PppModule,
        
        FuseSharedModule,
    ],
    exports     : [
        PostComponent
    ],
    providers: [AppUtilService]
})

export class PostModule
{
}
