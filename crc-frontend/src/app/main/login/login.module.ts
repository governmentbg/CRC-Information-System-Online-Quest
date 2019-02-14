import { NgModule } from '@angular/core';
import { LoginRoutingModule } from '../login/login-routing.module';
import { MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { FuseSharedModule } from '../../../@fuse/shared.module';

import { LoginComponent } from '../login/login.component';
import { AuthenticationService } from 'app/services/authentication.service';
import { SharedMaterialModule } from 'app/shared/shared-material.module';

@NgModule({
    declarations: [
        LoginComponent
    ],
    imports: [
        LoginRoutingModule,

        SharedMaterialModule,

        FuseSharedModule
    ],
    providers: []
})
export class LoginModule {
}
