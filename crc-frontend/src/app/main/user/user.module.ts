import {NgModule} from '@angular/core';
import {
    MatButtonModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatRippleModule,
    MatTableModule,
    MatToolbarModule
} from '@angular/material';

import {FuseSharedModule} from '../../../@fuse/shared.module';
import {FuseConfirmDialogModule, FuseSidebarModule} from '../../../@fuse/components';

import {UserComponent} from '../user/user.component';
import {UserService} from '../user/user.service';
import {UserListComponent} from '../user/user-list/user-list.component';

import {UserRoutingModule} from './user-routing.module';

// const routes: Routes = [
//     {
//         path     : '**',
//         component: UserComponent,
//         resolve  : {
//             contacts: UserService
//         }
//     }
// ];

@NgModule({
    declarations   : [
        UserComponent,
        UserListComponent,
        //UserFormDialogComponent
    ],
    imports        : [
        UserRoutingModule,

        MatButtonModule,
        MatCheckboxModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatMenuModule,
        MatRippleModule,
        MatTableModule,
        MatToolbarModule,

        FuseSharedModule,
        FuseConfirmDialogModule,
        FuseSidebarModule
    ],
    providers      : [
        UserService
    ],
    entryComponents: [
        //UserFormDialogComponent
    ]
})
export class UserModule
{
}
