import {Component, OnInit, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialog, MatDialogRef, MatPaginator, MatTableDataSource} from '@angular/material';
import {Subject} from 'rxjs';

import {fuseAnimations} from '@fuse/animations';
import {FuseConfirmDialogComponent} from '@fuse/components/confirm-dialog/confirm-dialog.component';
import {Company} from 'app/model/company';
import {AppUtilService} from 'app/main/util/app.util.service';
import {Router} from '@angular/router';
import {OperatorDialog} from './operator-dialog/operator-dialog.component';
import {OperatorService} from './operator-dialog/operator.service';
import { appConfig } from 'app/app.config';
import { AuthenticationService } from 'app/services/authentication.service';

@Component({
    selector: 'operator-administration',
    templateUrl: './operator-administration.component.html',
    styleUrls: ['./operator-administration.component.scss'],
    animations: fuseAnimations,
    encapsulation: ViewEncapsulation.None,
})
export class OperatorAdministrationComponent implements OnInit {
    @ViewChild('dialogContent')
    dialogContent: TemplateRef<any>;
    @ViewChild(MatPaginator) paginator: MatPaginator;

    contacts: any;
    user: any;
    dataSource: MatTableDataSource<Company> = new MatTableDataSource();
    displayedColumns = ['eikUri', 'name', 'date', 'status', 'actions'];
    selectedContacts: any[];
    checkboxes: {};
    dialogRef: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;

    // Private
    private _unsubscribeAll: Subject<any>;
    company: Company;

    currentUser: any;

    /**
     * Constructor
     *
     * @param {UserService} _userService
     * @param {MatDialog} _matDialog
     */
    constructor(
        private _operatorService: OperatorService,
        private _appService: AppUtilService,
        private _router: Router,
        private _authenticationService: AuthenticationService,
        public dialog: MatDialog
    ) {
        this.currentUser = this._authenticationService.currentUserValue;

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

        this.getAllCompaniesOperators();
        this.dataSource.paginator = this.paginator;        
    }

    getAllCompaniesOperators() {
        this._operatorService.getAllCompanies(this.currentUser.accessToken).subscribe(res => {
            this.dataSource.data = res;
        });
    }

    applyEIKFilter(filterValue: string) {
        if (filterValue !== '' && filterValue.length >= 3) {
            this._operatorService.getCompanyByEik(filterValue, this.currentUser.accessToken).subscribe(res => {
                this.dataSource.data = [];
                this.dataSource.data = res;
            });
        } else if (filterValue.length === 0) {
            this.getAllCompaniesOperators();
        }
    }

    applyURIFilter(filterValue: string) {
        if (filterValue !== '' && filterValue.length >= 3) {
            this._operatorService.getCompanyByURI(filterValue, this.currentUser.accessToken).subscribe(res => {
                this.dataSource.data = [];
                this.dataSource.data = res;
            });
        } else if (filterValue.length === 0) {
            this.getAllCompaniesOperators();
        }
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

    /**
     * Edit company
     *
     * @param company
     */
    editCompany(company): void {
        let edit = true;
        const dialogRef = this.dialog.open(OperatorDialog, {
            maxWidth: '100vw',
            maxHeight: '100vh',
            height: '100%',
            width: '100%',
            data: {edit: edit, company: company}
        });

        dialogRef.afterClosed().subscribe(() => {
            this.getAllCompaniesOperators();
        });
    }

    addCompany(): void {
        let edit = false;
        const dialogRef = this.dialog.open(OperatorDialog, {
            maxWidth: '95vw',
            maxHeight: '95vh',
            height: '95%',
            width: '95%',
            data: {edit: edit}
        });

        dialogRef.afterClosed().subscribe(() => {
            this.getAllCompaniesOperators();
        });
    }

    /**
     * Delete Contact
     */
    deleteCompany(company) {
        this._operatorService.deleteCompany(company.id, this.currentUser.accessToken).subscribe(res => {
            this._appService.opendSnackBar('Успешно изтрит потребител');

            this.getAllCompaniesOperators();
        });
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
    toggleStar(contactId): void {
        if (this.user.starred.includes(contactId)) {
            this.user.starred.splice(this.user.starred.indexOf(contactId), 1);
        }
        else {
            this.user.starred.push(contactId);
        }
    }
}
