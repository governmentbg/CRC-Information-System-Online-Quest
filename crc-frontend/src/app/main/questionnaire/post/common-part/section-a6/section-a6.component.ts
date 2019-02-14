import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-section-a6',
  templateUrl: './section-a6.component.html',
  styleUrls: ['./section-a6.component.scss']
})
export class SectionA6Component implements OnInit {
  postSecurityFormGroup: FormGroup;

  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit() {
    this.postSecurityFormGroup = this._formBuilder.group({

    });
  }

}
