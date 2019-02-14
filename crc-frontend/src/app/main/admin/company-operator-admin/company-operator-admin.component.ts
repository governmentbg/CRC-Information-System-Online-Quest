import { Component, OnInit, TemplateRef, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogRef, MatPaginator, MatTableDataSource } from '@angular/material';
import { Subject } from 'rxjs';

import { fuseAnimations } from '@fuse/animations';
import { FuseConfirmDialogComponent } from '@fuse/components/confirm-dialog/confirm-dialog.component';
import { User } from 'app/model/user';
import { appConfig } from 'app/app.config';
import { CompanyAdminService } from './company-admin.service';
import { AuthenticationService } from 'app/services/authentication.service';
import { AppUtilService } from 'app/main/util/app.util.service';
import { CrcUserDialog } from 'app/main/user/crc-user-administration/crc-user-dialog/crc-user-dialog.component';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
    selector: 'crc-user-administration',
    templateUrl: './company-operator-admin.component.html',
    animations: fuseAnimations,
    encapsulation: ViewEncapsulation.None
})
export class CompanyOperatorAdminComponent implements OnInit {
    //dataSource: FilesDataSource | null;
    @ViewChild('dialogContent')
    dialogContent: TemplateRef<any>;
    @ViewChild(MatPaginator) paginator: MatPaginator;

    contacts: any;
    user: User;
    dataSource: MatTableDataSource<User> = new MatTableDataSource();
    selection = new SelectionModel<User>(true, []);

    displayedColumns = ['firstName', 'lastName', 'email', 'activeTo', 'status'];
    selectedContacts: any[];
    checkboxes: {};
    dialogRef: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    currentUser: any;
    userActivation: boolean;

    // Private
    private _unsubscribeAll: Subject<any>;

    /**
     * Constructor
     *
     * @param {UserService} _userService
     * @param {MatDialog} _matDialog
     */
    constructor(
        public _matDialog: MatDialog,
        private _companyAdminService: CompanyAdminService,
        private _authService: AuthenticationService,
        private _appUtilService: AppUtilService
    ) {
        // Set the private defaults
        this._unsubscribeAll = new Subject();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void {
        this.currentUser = this._authService.currentUserValue;

        this.getManagedOperatorUsers();
        this.dataSource.paginator = this.paginator;
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next();
        this._unsubscribeAll.complete();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    getManagedOperatorUsers() {
        let id = this._appUtilService.getCurrentUserIdFromToken(this.currentUser.accessToken);

        this._companyAdminService.getManagedOperatorsByUser(id, this.currentUser.accessToken).subscribe(res => {
            // console.log(res);
            this.dataSource.data = res;
        });
    }

    saveUser() {
        this._authService.updateUser(this.user, this.currentUser.accessToken).subscribe(user => {
            this.user = user;
        });
    }

    getSelectedUser(event, selectedUserRow: User) {
        selectedUserRow.activationStatus = event.checked;
        this.user = selectedUserRow;
        this.saveUser();
    }

   
    /**
     * On selected change
     *
     * @param contactId
     */
    onSelectedChange(contactId): void {
        // this._userService.toggleSelectedContact(contactId);
    }

    /**
     * Toggle star
     *
     * @param contactId
     */
    // toggleStar(contactId): void {
    //     if (this.user.starred.includes(contactId)) {
    //         this.user.starred.splice(this.user.starred.indexOf(contactId), 1);
    //     }
    //     else {
    //         this.user.starred.push(contactId);
    //     }
    // }
}
