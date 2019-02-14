import {Component, Input, OnDestroy, OnInit, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {Subject} from 'rxjs';

import {fuseAnimations} from '../../../../@fuse/animations';
import {FuseConfirmDialogComponent} from '../../../../@fuse/components/confirm-dialog/confirm-dialog.component';

import {UserService} from '../user.service';

@Component({
    selector     : 'contacts-contact-list',
    templateUrl  : './user-list.component.html',
    styleUrls    : ['./user-list.component.scss'],
    encapsulation: ViewEncapsulation.None,
    animations   : fuseAnimations
})
export class UserListComponent implements OnInit, OnDestroy
{
    @ViewChild('dialogContent')
    dialogContent: TemplateRef<any>;

    contacts: any;
    user: any;
    @Input() dataSource: any[];
    displayedColumns = ['eikUri', 'name', 'date', 'status', 'actions'];
    selectedContacts: any[];
    checkboxes: {};
    dialogRef: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;

    // Private
    private _unsubscribeAll: Subject<any>;

    /**
     * Constructor
     *
     * @param {UserService} _userService
     * @param {MatDialog} _matDialog
     */
    constructor(
        private _userService: UserService,
        public _matDialog: MatDialog
    )
    {
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
        this.dataSource = [{name: 'test', email: 'test@test.sds', phone: '01231', jobTitle: 'test'}];
        // this._userService.onContactsChanged
        //     .pipe(takeUntil(this._unsubscribeAll))
        //     .subscribe(contacts => {
        //         this.contacts = contacts;

        //         this.checkboxes = {};
        //         contacts.map(contact => {
        //             this.checkboxes[contact.id] = false;
        //         });
        //     });

        // this._userService.onSelectedContactsChanged
        //     .pipe(takeUntil(this._unsubscribeAll))
        //     .subscribe(selectedContacts => {
        //         for ( const id in this.checkboxes )
        //         {
        //             if ( !this.checkboxes.hasOwnProperty(id) )
        //             {
        //                 continue;
        //             }

        //             this.checkboxes[id] = selectedContacts.includes(id);
        //         }
        //         this.selectedContacts = selectedContacts;
        //     });

        // this._userService.onUserDataChanged
        //     .pipe(takeUntil(this._unsubscribeAll))
        //     .subscribe(user => {
        //         this.user = user;
        //     });

        // this._userService.onFilterChanged
        //     .pipe(takeUntil(this._unsubscribeAll))
        //     .subscribe(() => {
        //         this._userService.deselectContacts();
        //     });
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

    /**
     * Edit contact
     *
     * @param contact
     */
    editContact(contact): void
    {
        // this.dialogRef = this._matDialog.open(UserFormDialogComponent, {
        //     panelClass: 'contact-form-dialog',
        //     data      : {
        //         contact: contact,
        //         action : 'edit'
        //     }
        // });

        // this.dialogRef.afterClosed()
        //     .subscribe(response => {
        //         if ( !response )
        //         {
        //             return;
        //         }
        //         const actionType: string = response[0];
        //         const formData: FormGroup = response[1];
        //         switch ( actionType )
        //         {
        //             /**
        //              * Save
        //              */
        //             case 'save':

        //                 this._userService.updateContact(formData.getRawValue());

        //                 break;
        //             /**
        //              * Delete
        //              */
        //             case 'delete':

        //                 this.deleteContact(contact);

        //                 break;
        //         }
        //     });
    }

    /**
     * Delete Contact
     */
    deleteContact(contact): void
    {
        this.confirmDialogRef = this._matDialog.open(FuseConfirmDialogComponent, {
            disableClose: false
        });

        this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';

        // this.confirmDialogRef.afterClosed().subscribe(result => {
        //     if ( result )
        //     {
        //         this._userService.deleteContact(contact);
        //     }
        //     this.confirmDialogRef = null;
        // });

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

    //    this._userService.updateUserData(this.user);
    }
}

// export class FilesDataSource extends DataSource<any>
// {
//     /**
//      * Constructor
//      *
//      * @param {UserService} _userService
//      */
//     constructor(
//         private _userService: UserService
//     )
//     {
//         super();
//     }

//     /**
//      * Connect function called by the table to retrieve one stream containing the data to render.
//      * @returns {Observable<any[]>}
//      */
//     // connect(): Observable<any[]>
//     // {
//     //     return this._userService.onContactsChanged;
//     // }

//     /**
//      * Disconnect
//      */
//     disconnect(): void
//     {
//     }
// }
