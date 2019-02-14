import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatSnackBar, MatSnackBarModule, MatTableModule, MatStepperModule } from '@angular/material';
import { TranslateModule } from '@ngx-translate/core';
import 'hammerjs';

import { FuseModule } from '@fuse/fuse.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseProgressBarModule, FuseSidebarModule, FuseThemeOptionsModule } from '@fuse/components';

import { fuseConfig } from 'app/fuse-config';

import { AppRoutingModule } from 'app/app.routing';
import { AppComponent } from 'app/app.component';
import { LayoutModule } from 'app/layout/layout.module';
import { AuthGuard } from './guards/auth.guard';
import { HttpErrorInterceptor } from './interceptors/http-error.interceptor';
import { SharedMaterialModule } from './shared/shared-material.module';
import { AppUtilService } from './main/util/app.util.service';
import { AuthenticationService } from './services/authentication.service';
import { StoreModule } from '@ngrx/store';
import { appReducers } from './store/reducers/app.reducers';
import { EffectsModule } from '@ngrx/effects';
import { QuestionnaireEffects } from './store/effects/questionnaire.effects';
import { environment } from 'environments/environment.hmr';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { CommonPartService } from './main/questionnaire/post/common-part/common-part.service';

@NgModule({
    declarations: [
        AppComponent,
    ],
    imports     : [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        AppRoutingModule,
        TranslateModule.forRoot(),


        //Redux
        StoreModule.forRoot(appReducers),
        EffectsModule.forRoot([QuestionnaireEffects]),
        !environment.production ? StoreDevtoolsModule.instrument() : [],

        // Material moment date module
        MatMomentDateModule,

        // Material
        SharedMaterialModule,

        // Fuse modules
        FuseModule.forRoot(fuseConfig),
        FuseProgressBarModule,
        FuseSharedModule,
        FuseSidebarModule,
        FuseThemeOptionsModule,
        MatSnackBarModule,
        // App modules
        LayoutModule,
    ],
    exports: [
        MatStepperModule, MatTableModule,
        SharedMaterialModule
    ],
    bootstrap   : [
        AppComponent
    ],
    providers: [
        AuthGuard,
        AppUtilService,
        AuthenticationService,
        CommonPartService,
        MatSnackBar,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: HttpErrorInterceptor,
            multi: true
        }
    ]
})
export class AppModule
{
}
