import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
    HttpResponse,
    HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { AppUtilService } from 'app/main/util/app.util.service';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
    constructor(private _appService: AppUtilService, private router: Router) {

    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    let errorMessage = '';
                    if (error.error instanceof ErrorEvent) {
                        // client-side error
                        errorMessage = `Error: ${error.error.message}`;
                        this._appService.opendSnackBar(errorMessage);
                    }
                    switch (error.status) {
                        case 400:
                            errorMessage = `${error.error.message}`;
                            this._appService.opendSnackBar(errorMessage);
                            break;
                        case 401:
                            errorMessage = 'Грешно портребителско име или парола!';
                            this._appService.opendSnackBar(errorMessage);
                            break;
                        case 404:
                        // this.router.navigate(['error/error-404']);
                        default:
                            break;
                    }
                    return throwError(errorMessage);
                })
            )
    }
}