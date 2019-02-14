import { Component, OnDestroy, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { Subject } from 'rxjs';
import { User } from 'app/model/user';
import { CrcUserService } from './crc-user.service';
import { MatCheckboxChange, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UsernamePassData } from 'app/model/usernamePassData';
import { Router } from '@angular/router';
import { appConfig } from 'app/app.config';
import { AuthenticationService } from 'app/services/authentication.service';
import {ValidatePassword} from 'app/validators/password.validator';

@Component({
    selector: 'crc-user-dialog',
    templateUrl: './crc-user-dialog.component.html',
})
export class CrcUserDialog implements OnInit, OnDestroy {
    form: FormGroup;

    // Private
    private _unsubscribeAll: Subject<any>;
    roles: any[] = [
        {value: 'ZES', desc: 'ЗЕС', selected: false},
        {value: 'POST', desc: 'ЗПУ', selected: false},
        {value: 'ADMIN', desc: 'Администратор', selected: false}
    ];
    selectedRoles: string[] = [];
    isRoleSelect: boolean;
    currentUser: any;
    rolesValidation: boolean = false;
    /**
     * Constructor
     *
     * @param {FormBuilder} _formBuilder
     */
    constructor(
        public dialogRef: MatDialogRef<CrcUserDialog>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private _formBuilder: FormBuilder,
        private _crcUserService: CrcUserService,
        private _router: Router,
        private authenticationService: AuthenticationService
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
        // Reactive Form
        this.form = this._formBuilder.group({
            firstName: ['', Validators.required],
            middleName: ['', Validators.required],
            lastName: ['', Validators.required],
            egn: ['', [Validators.required, Validators.minLength(10)]],
            username: ['', Validators.required],
            password: ['', Validators.required],
            repeatPassword: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            activeFrom: ['', Validators.required],
            activeTo: ['', Validators.required],
            userActivation: [''],
            roles: new FormArray([], Validators.required)
        }, {validator: ValidatePassword.validate});
        

        this.currentUser = this.authenticationService.currentUserValue;

        this.updateCrcUser(this.data);
    }

    collectCrcUserData() {
        let user = new User();
        user.firstName = this.form.get('firstName').value;
        user.lastName = this.form.get('lastName').value;
        user.middleName = this.form.get('middleName').value;
        user.egn = this.form.get('egn').value;
        user.email = this.form.get('email').value;
        user.activeFrom = this.form.get('activeFrom').value;
        user.activeTo = this.form.get('activeTo').value;
        user.roles = this.form.get('roles').value;
        user.activationStatus = !this.form.get('userActivation').value;

        user.usernamePassData = new UsernamePassData();
        user.usernamePassData.username = this.form.get('username').value;
        user.usernamePassData.password = this.form.get('password').value;
        return user;
    }

    createOrUpdateCrcUser() {
        let sendUser = this.collectCrcUserData();
        if (!this.data.edit) {
            if (this.form.get('roles').value.length === 0) {
                this.rolesValidation = true;
                this.form.get('roles').markAsUntouched;
                this.form.get('roles').setErrors({'incorrect': true});
            } 

            this._crcUserService.createCrcUser(sendUser, this.currentUser.accessToken).subscribe(res => {
                if (res) {
                    this.dialogRef.close();
                    this._router.navigate(['user/crc-user-administration']);
                }
            });
        } else {
            sendUser.id = this.data.crcUser.id;
            this._crcUserService.updateCrcUser(sendUser, this.currentUser.accessToken).subscribe(res => {
                if (res) {
                    this.dialogRef.close();
                    this._router.navigate(['user/crc-user-administration']);
                }
            });
        }
       
    }

    onCheckChange(event) {
        const formArray: FormArray = this.form.get('roles') as FormArray;
        if (this.form.get('roles').value.length === 0) {
            this.rolesValidation = false;

        }    
        /* Selected */
        if(event.checked){
          // Add a new control in the arrayForm
          
          formArray.push(new FormControl(event.source.value, Validators.required));
                       }
        /* unselected */
        else{
          // find the unselected element
          let i: number = 0;
      
          formArray.controls.forEach((ctrl: FormControl) => {
            if(ctrl.value == event.source.value) {
              // Remove the unselected element from the arrayForm
              this.rolesValidation = false;

              formArray.removeAt(i);
              return;
            }
      
            i++;
          });
        }
      }

    updateCrcUser(data) {
        console.log(data.crcUser);
        if (data.crcUser) {
            this.form.get('firstName').setValue(this.data.crcUser.firstName);
            this.form.get('middleName').setValue(this.data.crcUser.middleName);
            this.form.get('lastName').setValue(this.data.crcUser.lastName);
            this.form.get('egn').setValue(this.data.crcUser.egn);
            this.form.get('email').setValue(this.data.crcUser.email);

            this.form.get('activeFrom').setValue(this.data.crcUser.activeFrom);
            this.form.get('activeTo').setValue(this.data.crcUser.activeTo);
            this.form.get('username').setValue(this.data.crcUser.usernamePassData.username);
            this.form.get('password').setValue(this.data.crcUser.usernamePassData.password);
            this.form.get('repeatPassword').setValue((this.data.crcUser.usernamePassData.password));
            this.form.get('userActivation').setValue(!this.data.crcUser.activationStatus);

            const formArray: FormArray = this.form.get('roles') as FormArray;

            this.data.crcUser.roles.forEach(role => {
                this.roles.forEach(checkboxRole => {
                    if (role === checkboxRole.value) {
                        formArray.push(new FormControl(role));
                        checkboxRole.selected = true;
                    }
                });
            });
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

}
