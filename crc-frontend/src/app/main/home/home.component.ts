import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import { FuseTranslationLoaderService } from '@fuse/services/translation-loader.service';

import { locale as english } from './i18n/en';
import { locale as turkish } from './i18n/tr';
import { fuseAnimations } from '@fuse/animations';
import { AuthenticationService } from 'app/services/authentication.service';
import { AppUtilService } from '../util/app.util.service';
import { OperatorService } from '../user/operator-administration/operator-dialog/operator.service';
import { User } from 'app/model/user';
import { appConfig } from 'app/app.config';

@Component({
    selector   : 'home',
    templateUrl: './home.component.html',
    styleUrls  : ['./home.component.scss'],
    encapsulation: ViewEncapsulation.None,
    animations   : fuseAnimations
})
export class HomeComponent implements OnInit
{

    boards: any[];
    currentUser: User;
    companyToServe: any[] = [];
    userCompanies: any[] = [];
    currentUserToken: any;

    /**
     * Constructor
     *
     * @param {FuseTranslationLoaderService} _fuseTranslationLoaderService
     */
    constructor(
        private _fuseTranslationLoaderService: FuseTranslationLoaderService,
        private _appService: AppUtilService,
        private _authService: AuthenticationService,
        private _operatorService: OperatorService
    )
    {
        this._fuseTranslationLoaderService.loadTranslations(english, turkish);

    }

    ngOnInit() {
        this.currentUserToken = JSON.parse(localStorage.getItem('currentUser'));
        this.currentUser = JSON.parse(localStorage.getItem('user'));

        this.getUserCompanies(this.currentUser.companyToServe);       
    }

    getUserCompanies(companyToServe) {
        companyToServe.forEach(cmp => {
            this._operatorService.getCompanyById(cmp, this.currentUserToken.accessToken).subscribe(company => {
                console.log(company);
                this.userCompanies.push(company);
                localStorage.setItem('companies', JSON.stringify(this.userCompanies));
            })
        });

    }
}
