import { Component, OnInit, Input, NgModule, Output, EventEmitter } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: '[column]',
  templateUrl: './column.component.html',
  styleUrls: ['./column.component.scss']
})
export class ColumnComponent implements OnInit {

  @Input() public tableFormGroup: FormGroup;

  @Input() public rowId: any;

  @Input() public column: number;

  @Input() public index: number;

  @Input() public value: number;


  @Output() public onInputChange: EventEmitter<any> = new EventEmitter();
  constructor() { }

  ngOnInit() {
  }

  public inputChange(value, index) {
    value = parseFloat(value);
    if (isNaN(value)) {
      value = 0
    }

    this.onInputChange.emit({
      value: value,
      index: index
    });
  }
}

@NgModule({
  declarations: [ColumnComponent],
  imports: [ReactiveFormsModule],
  exports: [ColumnComponent]
})
export class ColumnModule {}
