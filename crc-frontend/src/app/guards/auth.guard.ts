import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router) { }


    canActivate(): boolean {
        const currentUser = localStorage.getItem('currentUser');

        if (currentUser != null) {
            return true;
        }
        this.router.navigate(['login']);
        return false;
    }

    // canActivate() {
    //     let currentUser = localStorage.getItem(appConfig.CURRENT_USER);

    //     if (currentUser == null) {
    //         this.router.navigate(['/login']);
    //         return false;
    //     } else {
    //         this.router.navigate(['/services']);
    //     }

    //     return true;
    // }
}
