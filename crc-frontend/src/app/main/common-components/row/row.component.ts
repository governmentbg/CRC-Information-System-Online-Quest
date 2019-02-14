import * as _ from 'lodash';

import { Component, OnInit, NgModule, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ColumnModule } from '../column/column.component';

@Component({
  selector: '[row]',
  templateUrl: './row.component.html',
  styleUrls: ['./row.component.scss']
})
export class RowComponent implements OnInit {

  @Input() public row: any;

  @Input() tableFormGroup: FormGroup;

  @Input() public columnsCount: number;

  @Output() public onInputChange: EventEmitter<any> = new EventEmitter();

  public prevValues;

  constructor(private _formBuilder: FormBuilder) {
  }
  
  ngOnInit() {
    this.prevValues = _.range(+this.columnsCount).map(() => 0);
  }

  public inputChange(evt) {
    const obj = {
      value: evt.value,
      idx: evt.index,
      prevValues: this.prevValues
    }
    this.onInputChange.emit(obj);
    this.prevValues[evt.index] = evt.value;
  }

}

@NgModule({
  declarations: [RowComponent],
  imports: [ReactiveFormsModule, CommonModule, FormsModule, ColumnModule],
  exports: [RowComponent]
})
export class RowModule {}
