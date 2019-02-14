import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { CommonPartService } from '../common-part/common-part.service';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'app/store/state/app.state';
import { selectQuestionnaireJson } from 'app/store/selectors/questionnaire.selectors';

@Component({
  selector: 'app-nps',
  templateUrl: './nps.component.html',
  styleUrls: ['./nps.component.scss']
})
export class NpsComponent implements OnInit {

  public groups: Array<any>[];

  public formGroup: FormGroup;

  questionnaire$ = this._store.pipe(select(selectQuestionnaireJson));

  constructor(private _store: Store<IAppState>, private _formBuilder: FormBuilder, private _commonPartService: CommonPartService) {
   
  }

  ngOnInit() {
    // this.questionnaire$.subscribe(data => {
    //   this.groups = data['questionnaires'].q2.data.groups;
    // })
  }

}
