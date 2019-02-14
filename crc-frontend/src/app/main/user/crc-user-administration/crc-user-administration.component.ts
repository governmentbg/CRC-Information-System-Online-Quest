import {Component, OnInit, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialog, MatDialogRef, MatPaginator, MatTableDataSource} from '@angular/material';
import {Subject} from 'rxjs';

import {fuseAnimations} from '@fuse/animations';
import {FuseConfirmDialogComponent} from '@fuse/components/confirm-dialog/confirm-dialog.component';
import {CrcUserDialog} from './crc-user-dialog/crc-user-dialog.component';
import {CrcUserService} from './crc-user-dialog/crc-user.service';
import {User} from 'app/model/user';
import { appConfig } from 'app/app.config';
import { AuthenticationService } from 'app/services/authentication.service';
import { AppUtilService } from 'app/main/util/app.util.service';

@Component({
    selector     : 'crc-user-administration',
    templateUrl  : './crc-user-administration.component.html',
    styleUrls    : ['./crc-user-administration.component.scss'],
    animations   : fuseAnimations,
    encapsulation: ViewEncapsulation.None
})
export class CRCUserAdministrationComponent implements OnInit
{
    //dataSource: FilesDataSource | null;
    @ViewChild('dialogContent')
    dialogContent: TemplateRef<any>;
    @ViewChild(MatPaginator) paginator: MatPaginator;

    contacts: any;
    user: any;
    dataSource: MatTableDataSource<User> = new MatTableDataSource();
    displayedColumns = ['firstName', 'lastName', 'email', 'activeTo', 'status', 'actions'];
    selectedContacts: any[];
    checkboxes: {};
    dialogRef: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    userToken: any;
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
        private _crcUserService: CrcUserService,
        private authenticationService: AuthenticationService,
        private appUtilService: AppUtilService
    )
    {
        this.userToken = this.authenticationService.currentUserValue;

        // Set the private defaults
        this._unsubscribeAll = new Subject();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void
    {

        this.getAllCrcUsers();
        this.dataSource.paginator = this.paginator;
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void
    {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next();
        this._unsubscribeAll.complete();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    getAllCrcUsers() {
        this._crcUserService.getAllUsers(this.userToken.accessToken).subscribe(res => {
            this.dataSource.data = res;
        });
    }

    applyEGNFilter(filterValue: string) {
        if (filterValue !== '' && filterValue.length === 3) {
            this._crcUserService.getCrcUserByEGN(filterValue, this.userToken.accessToken).subscribe(res => {
                console.log(res);
                this.dataSource.data = [];
                this.dataSource.data = res;
            });
        } else if (filterValue.length === 0) {
            this.getAllCrcUsers();
        }
    }

    addCrcUser() {
        let edit = false;
        const dialogRef = this._matDialog.open(CrcUserDialog, {
            maxWidth: '90vw',
            maxHeight: '95vh',
            height: '95%',
            width: '90%',
            data: {edit: edit}
        });

        dialogRef.afterClosed().subscribe(() => {
            this.getAllCrcUsers();
        });
    }

    /**
     * Edit contact
     *
     * @param contact
     */
    editCrcUser(user): void
    {
        let edit = true;
        const dialogRef = this._matDialog.open(CrcUserDialog, {
            maxWidth: '100vw',
            maxHeight: '100vh',
            height: '100%',
            width: '100%',
            data: {edit: edit, crcUser: user}
        });

        dialogRef.afterClosed().subscribe(() => {
            this.getAllCrcUsers();
        });
    }

    /**
     * Delete Contact
     */
    deleteCrcUser(crcUser: User): void
    {
        this._crcUserService.deleteCrcUser(crcUser.id, this.userToken.accessToken).subscribe(res => {
           // this._appService.opendSnackBar('Успешно изтрит потребител');
            this.getAllCrcUsers();
        });
    }

    /**
     * On selected change
     *
     * @param contactId
     */
    onSelectedChange(contactId): void
    {
        // this._userService.toggleSelectedContact(contactId);
    }

    /**
     * Toggle star
     *
     * @param contactId
     */
    toggleStar(contactId): void
    {
        if ( this.user.starred.includes(contactId) )
        {
            this.user.starred.splice(this.user.starred.indexOf(contactId), 1);
        }
        else
        {
            this.user.starred.push(contactId);
        }
    }
}
