import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { FuseConfigService } from '../../../@fuse/services/config.service';
import { fuseAnimations } from '../../../@fuse/animations';
import { Router } from '@angular/router';
import { AuthenticationService } from 'app/services/authentication.service';
import { first } from 'rxjs/operators';
import { AppUtilService } from '../util/app.util.service';

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    remeberUser = false;
    currentUser: any;
    /**
     * Constructor
     *
     * @param {FuseConfigService} _fuseConfigService
     * @param {FormBuilder} _formBuilder
     */
    constructor(
        private _fuseConfigService: FuseConfigService,
        private _formBuilder: FormBuilder,
        private router: Router,
        private _authService: AuthenticationService,
        private _appService: AppUtilService
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
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void {
        this.loginForm = this._formBuilder.group({
            usernameOrEmail: ['', Validators.required],
            password: ['', Validators.required]
        });
    }

    login() {
        const usernameOrEmail = this.loginForm.get('usernameOrEmail').value;
        const password = this.loginForm.get('password').value;
        let user = { usernameOrEmail: usernameOrEmail, password: password };

        this._authService.signIn(user).pipe(first()).subscribe(res => {

            if (res) {
                let id = this._appService.getCurrentUserIdFromToken(res.accessToken);

                this._authService.getUserById(id, res.accessToken).subscribe(user => {
                    this.currentUser = user;
                    localStorage.setItem('user', JSON.stringify({email: this.currentUser.email, companyToServe: this.currentUser.companyToServe}));
                    console.log(JSON.parse(localStorage.getItem('user')).companyToServe);
                });
                this.router.navigate(['/home']);
            }
        }, (err: Response) => {
            if (err.status) {
                this.router.navigate(['login']);
            }
        });
    }
}
