import { NgModule } from "@angular/core";
import { PppComponent } from "./ppp.component";
import { CommonModule } from "@angular/common";
import { ContactModule } from "app/main/common-components/contact/contact.modul";
import { FuseSharedModule } from "@fuse/shared.module";
import { GroupModule } from "app/main/common-components/group/group.component";
import { MatExpansionModule } from "@angular/material";

@NgModule({
    declarations: [PppComponent],
    imports: [
        CommonModule,               
        ContactModule,
        FuseSharedModule,
        GroupModule,
        MatExpansionModule,
    ],
    exports: [PppComponent]
})
export class PppModule {

}