import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {appConfig} from "app/app.config";
import {BehaviorSubject, Observable} from "rxjs";
import {map, retry} from "rxjs/operators";
import {User} from "app/model/user";
import { AppUtilService } from "app/main/util/app.util.service";

@Injectable({ providedIn: 'root' })
export class AuthenticationService {

    private currentUserSubject: BehaviorSubject<any>;
    private loggedUserSubject:BehaviorSubject<any>; 

    public currentUser: Observable<any>;
    public loggedUser: Observable<any>;

    headers = new HttpHeaders();
    options = {};

    constructor(private _httpClient: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<any>('');
        this.loggedUserSubject = new BehaviorSubject<any>(null);

        this.currentUser = this.currentUserSubject.asObservable();
        this.loggedUser = this.loggedUserSubject.asObservable();

        this.headers = this.headers.set('Content-Type', 'application/json; charset=utf-8');
        this.headers.delete('Authorization');
        this.options = { headers: this.headers };
    }

    public get currentUserValue(): any {
        return this.currentUserSubject.value;
    }

    loggedUserValue(): Observable<any> {
        return this.loggedUserSubject.asObservable();
    }

    setBehaviorView(behave: User) { 
        this.loggedUserSubject.next(behave); 
    } 

    signIn(user): Observable<any> {
        return this._httpClient.post<any>(appConfig.apiUrl + 'auth/signin', user)
            .pipe(map(user => {
                if (user && user.accessToken) {
                    let id = this.getCurrentUserIdFromToken(user.accessToken);
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.currentUserSubject.next(user);
                    //this.getUserById(id, user.accessToken);
                }

                return user;
            }));

    }

    signUp(user): Observable<any> {
        let response = this._httpClient.post<any>(appConfig.apiUrl + 'auth/signup', user)
            .pipe(retry(1));
        return response;
    }

    getUserById(id, authorization): Observable<User>  {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };
        return this._httpClient.get<User>(appConfig.apiUrl + 'profile/user/' + id, this.options)
        .pipe(map(user => {
            if (user) {
                this.loggedUserSubject.next(user);
            }

            return user;
        }));

    }

    updateUser(user, authorization) {
        this.headers = this.headers.set('Authorization', 'Bearer ' + authorization);
        this.options = { headers: this.headers };
        let response = this._httpClient.put<User>(appConfig.apiUrl + 'profile/user/', user, this.options);

        return response;
    }

    getCurrentUserIdFromToken(accessToken) {
        if (accessToken) {
            let jwtData = accessToken.split('.')[1];
            let decodeJwtJSONData = window.atob(jwtData);
            let decodedJwtData = JSON.parse(decodeJwtJSONData);

            return decodedJwtData.sub;
        }
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
        this.loggedUserSubject.next(null);
    }
}