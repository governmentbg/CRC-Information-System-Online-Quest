import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { appConfig } from 'app/app.config';
import { Company } from 'app/model/company';

@Injectable()
export class OperatorAdministrationService
{

    /**
     * Constructor
     *
     * @param {HttpClient} _httpClient
     */
    constructor(
        private _httpClient: HttpClient
    )
    {
    }

    getCompanyByEik(id) : Observable<Company[]>{
        let response = this._httpClient.get<Company[]>(appConfig.apiUrl + 'profile/co/' + id + '/eik');
        return response;
    }
    
    getCompanyByURI(id) : Observable<Company[]>{
        let response = this._httpClient.get<Company[]>(appConfig.apiUrl + 'profile/co/' + id + '/uri');
        return response;
    }

    deleteCompany(id): Observable<Object> {
        const params = new HttpParams()
        .set('id', id);

        let response = this._httpClient.delete<Object>(appConfig.apiUrl + 'profile/co', {params});
        return response;
    }
}
