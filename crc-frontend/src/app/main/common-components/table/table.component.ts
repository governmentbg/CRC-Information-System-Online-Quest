import * as _ from 'lodash';

import { Component, Input, NgModule, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, FormControl } from '@angular/forms';
import { MatExpansionModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { RowModule } from '../row/row.component';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent implements OnInit {
  @Input() public name: string = "tableName";

  @Input() public key: string;

  @Input() public groupCodeBase: string;

  @Input() public hasTotalSum: boolean;

  @Input() public rows: Array<any>;

  @Input() public columns: any;

  @Input() public hasValidation: boolean;

  public formGroup: FormGroup;
  public res;

  constructor(private _formBuilder: FormBuilder) {
    this.formGroup = this._formBuilder.group({});
  }

  ngOnInit() {
    this.rows.forEach(element => {
      const formBuilder = this._formBuilder.group({});

      element.values.forEach((value, idx) => {
        formBuilder.addControl(`${element.id}${idx}`, new FormControl());
      });
      this.formGroup.addControl(element.id, formBuilder);
    });

    this.res = _.range(this.columns.count).map(() => 0);
  }

  inputChange(evt) {
    this.res[evt.idx] += evt.value;
    this.res[evt.idx] -= evt.prevValues[evt.idx];
  }

  getData() {
    console.log(this.formGroup.controls);
  }
}

@NgModule({
  declarations: [TableComponent],
  imports: [FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatExpansionModule,
    RowModule],
  exports: [TableComponent]
})
export class TableModule { }