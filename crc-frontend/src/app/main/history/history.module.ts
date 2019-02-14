import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';
import { HistoryComponent } from "./history.component";
import { HistoryRoutingModule } from "./history-routing.module";
import { SharedMaterialModule } from "app/shared/shared-material.module";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { OperatorService } from "../user/operator-administration/operator-dialog/operator.service";

@NgModule({
    declarations: [HistoryComponent],
    imports: [
        CommonModule,
        FormsModule,
        HistoryRoutingModule,
        ReactiveFormsModule,
        SharedMaterialModule
    ],
    exports: [HistoryComponent],
    providers: [OperatorService]
})
export class HistoryModule {

}