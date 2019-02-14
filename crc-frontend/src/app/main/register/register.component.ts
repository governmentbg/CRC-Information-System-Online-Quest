import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { Component, OnDestroy, OnInit, ViewEncapsulation, ViewChild, ElementRef } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ValidatePassword } from 'app/validators/password.validator'
import { Subject, from, Observable } from 'rxjs';
import { takeUntil, startWith, map } from 'rxjs/internal/operators';

import { FuseConfigService } from '../../../@fuse/services/config.service';
import { fuseAnimations } from '../../../@fuse/animations';

import { User } from '../../model/user';
import { UsernamePassData } from 'app/model/usernamePassData';
import { Company } from 'app/model/company';
import { OperatorService } from '../user/operator-administration/operator-dialog/operator.service';
import { appConfig } from 'app/app.config';
import { MatChipInputEvent, MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material';
import { AuthenticationService } from 'app/services/authentication.service';
import { Router } from '@angular/router';
import { AppUtilService } from '../util/app.util.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations
})
export class RegisterComponent implements OnInit, OnDestroy {
    visible = true;
    selectable = true;
    removable = true;
    addOnBlur = true;
    separatorKeysCodes: number[] = [ENTER, COMMA];
    
    registerForm: FormGroup;

    // Private
    private _unsubscribeAll: Subject<any>;
    
    filteredCompanies: Observable<Company[]>;
    currentUser: any;
    options: Company[] = [];

    companies: any[] = [];
    companiesId: any[] = [];

    @ViewChild('auto') matAutocomplete: MatAutocomplete;
    @ViewChild('companyInput') companyInput: ElementRef<HTMLInputElement>;

    constructor(
        private _fuseConfigService: FuseConfigService,
        private _formBuilder: FormBuilder,
        private operatorService: OperatorService,
        private authService: AuthenticationService,
        private _appService: AppUtilService,
        private router: Router
    ) {
        // Configure the layout
        this._fuseConfigService.config = {
            layout: {
                navbar: {
                    hidden: true
                },
                toolbar: {
                    hidden: true
                },
                footer: {
                    hidden: true
                },
                sidepanel: {
                    hidden: true
                }
            }
        };

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
        this.currentUser = localStorage.getItem(appConfig.USER_TOKEN);
        this.getAllCompanies();
        this.registerForm = this._formBuilder.group({
            firstName: ['', Validators.required],
            middleName: ['', Validators.required],
            lastName: ['', Validators.required],
            egn: ['', [Validators.required, Validators.minLength(10)]],
            companyCtrl: [''],
            name: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required],
            repeatPassword: ['', Validators.required],
            selectedCompany: [''],
            recaptcha:['']
        }, {validator: ValidatePassword.validate});

        // Update the validity of the 'repeatPassword' field
        // when the 'password' field changes
        this.registerForm.get('password').valueChanges
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe(() => {
                this.registerForm.get('repeatPassword').updateValueAndValidity();
            });

        this.filteredCompanies = this.registerForm.get('companyCtrl').valueChanges
            .pipe(
                startWith(''),
                map(company => company ? this._filterCompanies(company) : this.options.slice())
            );
    }


    register(): void {
        const user = new User();

        user.firstName = this.registerForm.get('name').value;
        user.middleName = this.registerForm.get('middleName').value;
        user.lastName = this.registerForm.get('lastName').value;
        user.egn = this.registerForm.get('egn').value;
        user.email = this.registerForm.get('email').value;

        user.usernamePassData = new UsernamePassData();
        user.usernamePassData.username = this.registerForm.get('name').value;
        user.usernamePassData.password = this.registerForm.get('password').value;
        user.companyToServe = this.companiesId;

        this.authService.signUp(user).subscribe(res => {
            if (res) {
                this.router.navigate(['login']);
                this._appService.opendSnackBar('Успешно изтрит потребител');
            }
        });
        // }, (err: HttpErrorResponse) => {
        //     if (!err.error.success) {
        //         console.log(err.error.message)
        //         this._appService.opendSnackBar(err.error.message);
        //     }
       
    }

    getAllCompanies() {

        this.operatorService.getAllCompanies(this.currentUser).subscribe(res => {
            this.options = res;
        });
    }

    getSelectedCompany(event) {
        this.companies.push({id: event.option.value, name: event.option.viewValue});
        this.companiesId.push(event.option.value);
        this.companyInput.nativeElement.value = '';
        this.registerForm.get("selectedCompany").setValue(null);
    }

    displayFn(company?: Company): string | undefined {
        return company ? company.name : undefined;
    }

    add(event: MatChipInputEvent): void {
        if (!this.matAutocomplete.isOpen) {
          const input = event.input;
          const value = event.value;
    
          if ((value || '').trim()) {
            this.companies.push(value.trim());
          }
    
          // Reset the input value
          if (input) {
            input.value = '';
          }
    
          this.registerForm.get('companyCtrl').setValue(null);
        }
      }
    
      remove(company: string): void {
        const index = this.companies.indexOf(company);
    
        if (index >= 0) {
          this.companies.splice(index, 1);
        }
      }

    private _filterCompanies(value: string): Company[] {
        const filterValue = typeof value === 'string' ? value.toLowerCase() : '';

        return this.options.filter(company => company.name.toLowerCase().includes(filterValue));
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next();
        this._unsubscribeAll.complete();
    }
}