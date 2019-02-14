import { Component, Inject, ChangeDetectionStrategy, ChangeDetectorRef, ApplicationRef, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Company } from 'app/model/company';
import { User } from 'app/model/user';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Subject, Observable } from 'rxjs';
import { Representative } from 'app/model/representative';
import { Address } from 'app/model/address';
import { Ekatte } from 'app/model/ekatte';
import { AuthorizedPerson } from 'app/model/authorizedPerson';
import { UsernamePassData } from 'app/model/usernamePassData';
import { OperatorService } from './operator.service';
import { Router } from '@angular/router';
import { startWith, map, filter } from 'rxjs/operators';
import { appConfig } from 'app/app.config';
import { AuthenticationService } from 'app/services/authentication.service';
import { serializePath } from '@angular/router/src/url_tree';

export interface State {
    flag: string;
    name: string;
    population: string;
}
/**
 * @title Dialog Overview
 */
@Component({
    selector: 'operator-dialog',
    templateUrl: 'operator-dialog.component.html',
    styleUrls: ['operator-dialog.component.scss'],

})
export class OperatorDialog implements OnInit {
    form: FormGroup;

    signingType: boolean = true;

    // Horizontal Stepper
    horizontalStepperStep1: FormGroup;
    horizontalStepperStep2: FormGroup;
    horizontalStepperStep3: FormGroup;

    options: Company[] = [];
    company: Company;
    optionsUser: User[] = [];
    user: User;

    filteredCompanies: Observable<Company[]>;
    filteredUsers: Observable<User[]>;
    filteredRegions: string[] = ['One', 'Two', 'Three'];

    currentUser: any;
    namePrefix: string;
    namePrefixAuthorizedPerson: string;

    constructor(
        public dialogRef: MatDialogRef<OperatorDialog>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private _formBuilder: FormBuilder,
        private operatorService: OperatorService,
        private _router: Router,
        public applicationRef: ApplicationRef,
        private authService: AuthenticationService
    ) {
        this.currentUser = this.authService.currentUserValue;
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    ngOnInit() {
        this.currentUser = JSON.parse(localStorage.getItem(appConfig.USER_TOKEN));
        this.operatorService.getPlaces(this.currentUser.accessToken).subscribe(res => {
            this.filteredRegions = res;
        });
        this.getAllCompanies();

        // Horizontal Stepper form steps
        this.horizontalStepperStep1 = this._formBuilder.group({
            eik: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(13)]],
            uri: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(15)]],
            operatorName: ['', Validators.required],
            legalForm: ['', Validators.required],
            firstName: ['', Validators.required],
            middleName: ['', Validators.required],
            companyPosition: ['', Validators.required],
            lastName: ['', Validators.required],
            employeeCount: ['', Validators.required],
            tradeMark: ['', Validators.required],
            companyCtrl: [],
            phone: ['', [Validators.required, Validators.minLength(10)]],
            fax: [''],
            companyEmail: ['', [Validators.required, Validators.email]]
        });

        this.horizontalStepperStep2 = this._formBuilder.group({
            address: ['', Validators.required],
            postalCode: ['', [Validators.required, Validators.minLength(4)]],
            addressStreet: ['', Validators.required]
        });

        this.horizontalStepperStep3 = this._formBuilder.group({
            authorizedPersonFirstName: ['', Validators.required],
            authorizedPersonMiddleName: ['', Validators.required],
            authorizedPersonLastName: ['', Validators.required],
            authorizedPersonEGN: ['', [Validators.required, Validators.minLength(10)]],
            authorizedPersonEmail: ['', [Validators.required, Validators.email]],
            serialNumber: ['', [Validators.required, Validators.minLength(10)]],
            userCtrl: [],
            supplierOfAuthorizedService: ['', [Validators.required, Validators.email]],
            authorizedPersonUsername: ['', Validators.required],
            authorizedPersonPassword: [''],
            authorizedPersonRepeatPassword: ['']
        });

        this.updateCompany(this.data);
        this.filteredCompanies = this.horizontalStepperStep1.get('companyCtrl').valueChanges
            .pipe(
                startWith(''),
                map(company => company ? this._filterCompanies(company) : this.options.slice())
            );

        this.updateUser(this.data);
        this.filteredUsers = this.horizontalStepperStep3.get('userCtrl').valueChanges
            .pipe(
                startWith(''),
                map(user => user ? this._filteredUsers(user) : this.optionsUser.slice())
            );
    }

    getAllCompanies() {

        this.operatorService.getAllCompanies(this.currentUser.accessToken).subscribe(res => {
            this.options = res;
        });
    }

    displayFn(company?: Company): string | undefined {
        return company ? company.pid : undefined;
    }

    displayFnUser(user?: User): String | undefined{
        return user ? user.lastName : undefined;
    }

    displayAddress(address?: any): string | undefined {
        return address ? address.fullName : undefined;
    }

    getSelectedCompany(event) {
        this.updateCompany({ company: event });
    }

    getSelectedUser(event){
        //console.log(event);
        this.updateUser({ user: event });
    }

    collectCompanyData() {
        let company = new Company();
        company.pid = this.horizontalStepperStep1.get('eik').value;
        company.uid = this.horizontalStepperStep1.get('uri').value;
        company.name = this.horizontalStepperStep1.get('operatorName').value;
        company.legalForm = this.horizontalStepperStep1.get('legalForm').value;
        company.companyTrademark = this.horizontalStepperStep1.get('tradeMark').value;
        company.employeesCount = this.horizontalStepperStep1.get('employeeCount').value;
        company.phone = this.horizontalStepperStep1.get('phone').value;
        company.fax = this.horizontalStepperStep1.get('fax').value;
        company.companyEmailWeb = this.horizontalStepperStep1.get('companyEmail').value;

        company.representative = new Representative();
        company.representative.namePrefix = this.namePrefix;
        company.representative.firstName = this.horizontalStepperStep1.get('firstName').value;
        company.representative.middleName = this.horizontalStepperStep1.get('middleName').value;
        company.representative.lastName = this.horizontalStepperStep1.get('lastName').value;
        company.representative.position = this.horizontalStepperStep1.get('companyPosition').value;

        company.address = new Address();
        company.address.address = this.horizontalStepperStep2.get('addressStreet').value;
        company.address.postalCode = this.horizontalStepperStep2.get('postalCode').value;


        company.address.ekatte = new Ekatte();
        company.address.ekatte = this.horizontalStepperStep2.get('address').value;

        company.authorizedPerson = new AuthorizedPerson();
        company.authorizedPerson.namePrefix = this.namePrefixAuthorizedPerson;
        company.authorizedPerson.firstName = this.horizontalStepperStep3.get('authorizedPersonFirstName').value;
        company.authorizedPerson.middleName = this.horizontalStepperStep3.get('authorizedPersonMiddleName').value;
        company.authorizedPerson.lastName = this.horizontalStepperStep3.get('authorizedPersonLastName').value;
        company.authorizedPerson.egn = this.horizontalStepperStep3.get('authorizedPersonEGN').value;
        company.authorizedPerson.email = this.horizontalStepperStep3.get('authorizedPersonEmail').value;

        company.authorizedPerson.usernamePassData = new UsernamePassData();
        company.authorizedPerson.usernamePassData.username = this.horizontalStepperStep3.get('authorizedPersonUsername').value;
        company.authorizedPerson.usernamePassData.password = '1234';

        return company;
    }

    createOrUpdateCompanyOperator() {

        let company = this.collectCompanyData();

        if (!this.data.edit) {
            this.operatorService.createCompany(company, this.currentUser.accessToken).subscribe(res => {
                console.log(res);
                this.dialogRef.close();
                this._router.navigate(['user/operator-administration']);
            })
               
        } else {
            company.id = this.data.company.id;
            company.authorizedPerson.id = this.data.company.authorizedPerson.id;
            this.operatorService.updateCompany(company, this.currentUser.accessToken).subscribe(
                res => {
                    if (res) {
                        this._router.navigate(['user/operator-administration']);
                        this.dialogRef.close();
                    }
                }
            );
        }

    }

    updateCompany(data) {
         console.log(data.company);
        this.data = { edit: data.edit, company: data.company };
        if (data.company) {
            this.horizontalStepperStep1.get('eik').setValue(this.data.company.pid);
            this.horizontalStepperStep1.get('uri').setValue(this.data.company.uid);
            this.horizontalStepperStep1.get('operatorName').setValue(this.data.company.name);
            this.horizontalStepperStep1.get('legalForm').setValue(this.data.company.legalForm);
            this.horizontalStepperStep1.get('tradeMark').setValue(this.data.company.companyTrademark);
            this.horizontalStepperStep1.get('employeeCount').setValue(this.data.company.employeesCount);

            // namePrefix = 'Mr';
            this.namePrefix = this.data.company.representative.namePrefix;
            this.horizontalStepperStep1.get('firstName').setValue(this.data.company.representative.firstName);
            this.horizontalStepperStep1.get('middleName').setValue(this.data.company.representative.middleName);
            this.horizontalStepperStep1.get('lastName').setValue(this.data.company.representative.lastName);
            this.horizontalStepperStep1.get('companyPosition').setValue(this.data.company.representative.position);
            this.horizontalStepperStep1.get('phone').setValue(this.data.company.phone);
            this.horizontalStepperStep1.get('fax').setValue(this.data.company.fax);
            this.horizontalStepperStep1.get('companyEmail').setValue(this.data.company.companyEmailWeb);

            this.horizontalStepperStep2.get('addressStreet').setValue(this.data.company.address.address);
            this.horizontalStepperStep2.get('postalCode').setValue(this.data.company.address.postalCode);
            
            // ress.postCode = "4444";

            //ress.ekatte.id = '00151';
            this.horizontalStepperStep2.get('address').setValue(this.data.company.address.ekatte.fullName);

            // this.namePrefixAuthorizedPerson = this.data.company.authorizedPerson.namePrefix;
            // this.horizontalStepperStep3.get('authorizedPersonFirstName').setValue(this.data.company.authorizedPerson.firstName);
            // this.horizontalStepperStep3.get('authorizedPersonMiddleName').setValue(this.data.company.authorizedPerson.middleName);
            // this.horizontalStepperStep3.get('authorizedPersonLastName').setValue(this.data.company.authorizedPerson.lastName);
            // this.horizontalStepperStep3.get('authorizedPersonEGN').setValue(this.data.company.authorizedPerson.egn);
            // this.horizontalStepperStep3.get('authorizedPersonEmail').setValue(this.data.company.authorizedPerson.email);

            this.horizontalStepperStep3.get('authorizedPersonUsername').setValue(this.data.company.authorizedPerson.usernamePassData.username);
            //horizedPerson.usernamePassData.password = '1234';
        }
    }

    updateUser(data){
        // this.data = {edit: data.edit, user: data.user};

        if(data.user){
            console.log(data.user);

            //this.namePrefixAuthorizedPerson = this.data.company.authorizedPerson.namePrefix;
            this.horizontalStepperStep3.get('authorizedPersonFirstName').setValue(data.user.firstName);
            this.horizontalStepperStep3.get('authorizedPersonMiddleName').setValue(data.user.middleName);
            this.horizontalStepperStep3.get('authorizedPersonLastName').setValue(data.user.lastName);
            this.horizontalStepperStep3.get('authorizedPersonEGN').setValue(data.user.egn);
            this.horizontalStepperStep3.get('authorizedPersonEmail').setValue(data.user.email);
        }
    }

    private _filterCompanies(value: string): Company[] {
        const filterValue = typeof value === 'string' ? value.toLowerCase() : '';

        return this.options.filter(company => company.pid.toLowerCase().includes(filterValue));
    }

    private _filteredUsers(value: string): User[]{

        if(value.length >= 3){
            const filterValue = typeof value === 'string' ? value : '';

            this.operatorService.getUserByCriteria(filterValue, this.currentUser.accessToken).subscribe(res => {
                this.optionsUser = res;
            });

            return this.optionsUser.filter(user => user.email.includes(filterValue));
        }
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Finish the horizontal stepper
     */
    finishHorizontalStepper(): void {
        alert('You have finished the horizontal stepper!');
    }

    /**
     * Finish the vertical stepper
     */
    finishVerticalStepper(): void {
        alert('You have finished the vertical stepper!');
    }
}