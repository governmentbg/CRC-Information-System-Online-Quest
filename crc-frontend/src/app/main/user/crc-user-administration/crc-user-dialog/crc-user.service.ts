import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { appConfig } from "app/app.config";
import { User } from "app/model/user";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { Company } from "app/model/company";

@Injectable()
export class CrcUserService {
    headers = new HttpHeaders();
    options = {};
    
    constructor(private _httpClient: HttpClient){
        this.headers = this.headers.set('Content-Type', 'application/json; charset=utf-8');
        this.headers.delete('Authorization');
        this.options = { headers: this.headers };
    }

    createCrcUser(crcUser: User, authorization: string): Observable<User> {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.post<User>(appConfig.apiUrl + 'profile/crc/user', crcUser, this.options);

        return response;
    }

    getAllUsers(authorization: string) {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.get<User[]>(appConfig.apiUrl + 'profile/crc/users/all', this.options);

        return response;
    }

    getCrcUserByEGN(id: string, authorization: string): Observable<User[]> {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };
        let response = this._httpClient.get<User[]>(appConfig.apiUrl + 'profile/crc/users/search/egn?pid=' + id, this.options);

        return response;
    }

    deleteCrcUser(id: string, authorization: string) {
        const params = new HttpParams()
        .set('id', id);

        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { 
            headers: this.headers,
            params: params
        };
       
        let response = this._httpClient.delete<User>(appConfig.apiUrl + 'profile/crc/user', this.options);

        return response;
    }

    updateCrcUser(crcUser, authorization: string): Observable<User> {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };

        let response = this._httpClient.put<User>(appConfig.apiUrl + 'profile/crc/user', crcUser, this.options);

        return response;
    }
}