import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSelectModule, MatStepperModule, MatDatepickerModule, MatCheckboxModule, MatCardModule } from '@angular/material';
import { MatDividerModule } from '@angular/material/divider';

import { FuseSharedModule } from '../../../../@fuse/shared.module';

import { CreateUserComponent } from '../create-user/create-user.component';

const routes: Routes = [
    {
        path     : '',
        component: CreateUserComponent
    }
];

@NgModule({
    declarations: [
        CreateUserComponent
    ],
    imports     : [
        RouterModule.forChild(routes),

        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatSelectModule,
        MatStepperModule,
        MatDatepickerModule,
        MatCheckboxModule, 
        MatCardModule,
        MatDividerModule,
        
        FuseSharedModule,
    ]
})
export class CreateUserModule
{
}
