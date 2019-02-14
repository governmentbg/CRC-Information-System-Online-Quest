import { HttpClient, HttpHeaders } from "@angular/common/http";
import { appConfig } from "app/app.config";
import { User } from "app/model/user";
import { Injectable } from "@angular/core";

@Injectable()
export class CompanyAdminService {

    headers = new HttpHeaders();
    options = {};
    
    constructor(private _httpClient: HttpClient) {
        this.headers = this.headers.set('Content-Type', 'application/json; charset=utf-8');
        this.headers.delete('Authorization');
        this.options = { headers: this.headers };
    }

    getManagedOperatorsByUser(id, authorization) {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.get<User[]>(appConfig.apiUrl + 'profile/user/' + id + '/operators', this.options);

        return response;
    }
}