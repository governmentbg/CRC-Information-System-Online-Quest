import { NgModule } from "@angular/core";
import { QuestionnaireComponent } from "./questionnaire.component";
import { QuestionnaireRoutingModule } from "./questionnaire-routing.module";
import { SharedMaterialModule } from "app/shared/shared-material.module";
import { FuseSharedModule } from "@fuse/shared.module";
import { CommonModule } from "@angular/common";

@NgModule({
    declarations: [QuestionnaireComponent],
    imports: [
        QuestionnaireRoutingModule,
        CommonModule,

        SharedMaterialModule,
        FuseSharedModule,

    ],
    exports: [QuestionnaireComponent]
})
export class QuestionnaireModule {

}