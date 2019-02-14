import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';

@Injectable()
export class LoggedinGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate(): boolean {
      //  let currentUser = localStorage.getItem(appConfig.CURRENT_USER);

        // if (currentUser == null) {
        //     return true;
        // }

        return true;
    }
}
