import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, MatDialogModule, MatSelectModule, MatCheckboxModule, MatStepperModule, MatPaginatorModule, MatMenuModule, MatCardModule, MatRadioModule, MatTableModule, MatAutocompleteModule } from '@angular/material';
import { MatDividerModule } from '@angular/material/divider';

import { FuseSharedModule } from '../../../../@fuse/shared.module';

import { OperatorAdministrationComponent } from '../operator-administration/operator-administration.component';
import { AppUtilService } from 'app/main/util/app.util.service';
import { OperatorDialog } from './operator-dialog/operator-dialog.component';
import { OperatorService } from './operator-dialog/operator.service';
import { AuthenticationService } from 'app/services/authentication.service';
import { SharedMaterialModule } from 'app/shared/shared-material.module';

const routes: Routes = [
    {
        path: '',
        component: OperatorAdministrationComponent
    }
];

@NgModule({
    declarations: [
        OperatorAdministrationComponent,
        OperatorDialog
    ],
    entryComponents: [OperatorDialog],
    imports: [
        RouterModule.forChild(routes),

        SharedMaterialModule,
        FuseSharedModule,
    ],
    providers: [
        OperatorService,
        AppUtilService,
        AuthenticationService
    ]
})
export class OperatorAdministrationModule {
}
