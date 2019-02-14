import { MatSnackBar } from "@angular/material";
import { Injectable } from "@angular/core";
import { AuthenticationService } from "app/services/authentication.service";
import { UseExistingWebDriver } from "protractor/built/driverProviders";
import { User } from "app/model/user";
import { Subject, Observable, BehaviorSubject } from "rxjs";

@Injectable()
export class AppUtilService {
    
   // loggedUserSubject = new BehaviorSubject<User>(new User());
  //  public loggedUser: Observable<any>;

    userToken: any;

    private selectedIndex = new BehaviorSubject<any>(0);

    constructor(public snackBar: MatSnackBar, private authService: AuthenticationService) {
        this.userToken = this.authService.currentUserValue;
      //  this.loggedUser = this.loggedUserSubject.asObservable();
    }

    opendSnackBar(message: string) {
        this.snackBar.open(message, '', {
            duration: 2000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
        });
    }

    getCurrentUserIdFromToken(accessToken) {
        if (accessToken) {
            let jwtData = accessToken.split('.')[1];
            let decodeJwtJSONData = window.atob(jwtData);
            let decodedJwtData = JSON.parse(decodeJwtJSONData);

            return decodedJwtData.sub;
        }
    }

    saveSelectedTabIndex(index: number) {
        this.selectedIndex.next(index);
    }

    getSelectedIndex(): Observable<any> {
        return this.selectedIndex.asObservable();
    }

    // public get getLoggedUserValue(): any {
    //     return this.loggedUserSubject.value;
    // }

    // getCurrentLoggedUser(authorization) {
    //     let id = this.getCurrentUserIdFromToken(authorization);

    //     this.authService.getUserById(id, authorization).subscribe(user => {
    //         this.loggedUserSubject.next(user);
    //     })
    // }
}