import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, MatDialogModule, MatSelectModule, MatCheckboxModule,MatStepperModule, MatPaginatorModule, MatMenuModule, MatCardModule, MatRadioModule, MatTableModule, MatDatepickerModule, MatAutocompleteModule } from '@angular/material';
import { MatDividerModule } from '@angular/material/divider';

import { FuseSharedModule } from '../../../../@fuse/shared.module';
import { CompanyOperatorAdminComponent } from './company-operator-admin.component';
import { CompanyAdminService } from './company-admin.service';
import { AuthenticationService } from 'app/services/authentication.service';
import { OperatorDialog } from 'app/main/user/operator-administration/operator-dialog/operator-dialog.component';
import { SharedMaterialModule } from 'app/shared/shared-material.module';

const routes: Routes = [
    {
        path     : '',
        component: CompanyOperatorAdminComponent
    }
];

@NgModule({
    declarations: [
        CompanyOperatorAdminComponent
    ],
    imports     : [
        RouterModule.forChild(routes),

        SharedMaterialModule,
        FuseSharedModule,
    ],
    providers: [CompanyAdminService, AuthenticationService]
})
export class CompanyOperatorAdminModule
{
}
