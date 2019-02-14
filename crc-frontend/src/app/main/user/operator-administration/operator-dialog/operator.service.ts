import { Company } from "app/model/company";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { appConfig } from "app/app.config";
import { Observable, BehaviorSubject, Subject } from "rxjs";
import { Injectable } from "@angular/core";
import { share } from "rxjs/operators";
import { User } from "app/model/user";

@Injectable()
export class OperatorService {
    headers = new HttpHeaders();
    options = {};

    constructor(private _httpClient: HttpClient) {
        this.headers = this.headers.set('Content-Type', 'application/json; charset=utf-8');
        this.headers.delete('Authorization');
        this.options = { headers: this.headers };
    }

    createCompany(newCompany: Company, authorization: string): Observable<Company>{
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.post<Company>(appConfig.apiUrl + 'profile/co', newCompany, this.options);
        return response.pipe(share());
    }

    getAllCompanies(authorization: string) {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };
       
        let response = this._httpClient.get<Company[]>(appConfig.apiUrl + 'profile/co/all', this.options);

        return response;
    }

    updateCompany(company: Company, authorization: string): Observable<Company> {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.put<Company>(appConfig.apiUrl + 'profile/co', company, this.options);
        return response;
    }

    getCompanyByEik(id, authorization: string) : Observable<Company[]>{
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.get<Company[]>(appConfig.apiUrl + 'profile/co/' + id + '/eik', this.options);
        return response;
    }
    
    getCompanyByURI(id, authorization: string) : Observable<Company[]>{
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.get<Company[]>(appConfig.apiUrl + 'profile/co/' + id + '/uri', this.options);
        return response;
    }

    getCompanyById(id, authorization: string) : Observable<Company>{
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.get<Company>(appConfig.apiUrl + 'profile/co/' + id, this.options);
        return response;
    }

    deleteCompany(id, authorization: string): Observable<Object> {
        const params = new HttpParams()
        .set('id', id);
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { 
            headers: this.headers,
            params: params
        };

        let response = this._httpClient.delete<Object>(appConfig.apiUrl + 'profile/co', this.options);
        return response;
    }

    getPlaces(authorization: string): Observable<any[]> {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.post<any[]>(appConfig.apiUrl + 'ekatte/place', {place: 'Соф'}, this.options);
        return response;
    }

    getUserByCriteria(criteria: string, authorization: string): Observable<User[]>{
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.post<User[]>(appConfig.apiUrl + 'profile/user/search/all', {criteria: criteria}, this.options);
        return response;
    }
}