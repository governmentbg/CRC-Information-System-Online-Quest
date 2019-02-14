import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArrayName, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-representative-operator',
  templateUrl: './representative-operator.component.html',
  styleUrls: ['./representative-operator.component.scss']
})
export class RepresentativeOperatorComponent implements OnInit {
  secondFormGroup: FormGroup;

  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit() {
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

}
