import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CommonPartService } from '../common-part/common-part.service';
import { Store, select } from '@ngrx/store';
import { selectQuestionnaireJson } from 'app/store/selectors/questionnaire.selectors';
import { IAppState } from 'app/store/state/app.state';

@Component({
  selector: 'app-ppp',
  templateUrl: './ppp.component.html',
  styleUrls: ['./ppp.component.scss']
})
export class PppComponent implements OnInit {

  public groups: Array<any>[];

  public formGroup: FormGroup;
  
  questionnaire$ = this._store.pipe(select(selectQuestionnaireJson));

  constructor(private _store: Store<IAppState>, private _formBuilder: FormBuilder, private _commonPartService: CommonPartService) {
    
  }

  ngOnInit() {
    // this.questionnaire$.subscribe(data => {
    //   this.groups = data['questionnaires'].q3.data.groups;
    // })
  }

}
