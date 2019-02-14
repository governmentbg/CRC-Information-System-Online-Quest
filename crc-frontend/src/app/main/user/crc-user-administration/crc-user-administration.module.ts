import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, MatDialogModule, MatSelectModule, MatCheckboxModule,MatStepperModule, MatPaginatorModule, MatMenuModule, MatCardModule, MatRadioModule, MatTableModule, MatDatepickerModule } from '@angular/material';
import { MatDividerModule } from '@angular/material/divider';

import { FuseSharedModule } from '../../../../@fuse/shared.module';

import { CRCUserAdministrationComponent } from './crc-user-administration.component';
import { CrcUserDialog } from './crc-user-dialog/crc-user-dialog.component';
import { CrcUserService } from './crc-user-dialog/crc-user.service';
import { AppUtilService } from 'app/main/util/app.util.service';
import { AuthenticationService } from 'app/services/authentication.service';
import { SharedMaterialModule } from 'app/shared/shared-material.module';

const routes: Routes = [
    {
        path     : '',
        component: CRCUserAdministrationComponent
    }
];

@NgModule({
    declarations: [
        CRCUserAdministrationComponent,
        CrcUserDialog
    ],
    entryComponents: [CrcUserDialog],
    imports     : [
        RouterModule.forChild(routes),
        SharedMaterialModule,

        FuseSharedModule,
    ],
    providers: [CrcUserService, AuthenticationService, AppUtilService]
})
export class CRCUserAdministrationModule
{
}
