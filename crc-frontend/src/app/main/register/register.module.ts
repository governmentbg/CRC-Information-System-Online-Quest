import { NgModule } from '@angular/core';
import { RegisterRoutingModule } from '../../main/register/register-routing.module';
import { MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatIconModule, MatInputModule, MatAutocompleteModule, MatDatepickerModule, MatGridListModule, MatCardModule, MatStepperModule, MatChipsModule } from '@angular/material';

import { FuseSharedModule } from '../../../@fuse/shared.module';

import { RegisterComponent } from '../register/register.component';
import { OperatorService } from '../user/operator-administration/operator-dialog/operator.service';
import { AuthenticationService } from 'app/services/authentication.service';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxCaptchaModule } from 'ngx-captcha';
import { SharedMaterialModule } from 'app/shared/shared-material.module';

@NgModule({
    declarations: [
        RegisterComponent
    ],
    imports     : [
        RegisterRoutingModule,

        SharedMaterialModule,
        ReactiveFormsModule,
        NgxCaptchaModule,
        
        FuseSharedModule
    ], 
    providers: [OperatorService, AuthenticationService]
})
export class RegisterModule
{
}
