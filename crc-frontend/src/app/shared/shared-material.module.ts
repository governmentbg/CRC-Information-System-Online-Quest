import { NgModule } from '@angular/core';
import {
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatStepperModule,
    MatPaginatorModule,
    MatMenuModule,
    MatCardModule,
    MatRadioModule,
    MatDividerModule,
    MatTableModule,
    MatDialogModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatGridListModule,
    MatChipsModule,
    MatTabsModule
} from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({

    imports: [

        MatIconModule,
        MatInputModule,
        MatButtonModule,
        MatFormFieldModule,
        MatSelectModule,
        MatStepperModule,
        MatPaginatorModule,
        MatMenuModule, 
        MatCardModule,
        MatRadioModule,
        MatDividerModule,
        MatTableModule,
        MatCheckboxModule,
        MatDialogModule,
        MatAutocompleteModule,
        MatDatepickerModule,
        MatGridListModule,
        MatChipsModule,      
        MatTabsModule

    ],
    exports: [
        MatIconModule,
        MatInputModule,
        MatButtonModule,
        MatFormFieldModule,
        MatSelectModule,
        MatStepperModule,
        MatPaginatorModule,
        MatMenuModule, 
        MatCardModule,
        MatRadioModule,
        MatDividerModule,
        MatTableModule,
        MatCheckboxModule,
        MatDialogModule,
        MatAutocompleteModule,
        MatDatepickerModule,
        MatGridListModule,
        MatChipsModule,      
        MatTabsModule
    ],
    providers: []
})
export class SharedMaterialModule {
}
