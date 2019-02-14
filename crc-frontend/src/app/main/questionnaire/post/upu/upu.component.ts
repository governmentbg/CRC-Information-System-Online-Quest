import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { CommonPartService } from '../common-part/common-part.service';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'app/store/state/app.state';
import { selectQuestionnaireJson } from 'app/store/selectors/questionnaire.selectors';
import { GetQuestionnaire } from 'app/store/actions/questionnaire.actions';
import { Subject, Observable } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';

@Component({
  selector: 'app-upu',
  templateUrl: './upu.component.html',
  styleUrls: ['./upu.component.scss']
})
export class UpuComponent implements OnInit, OnDestroy {
  
  public groups: Subject<Array<any>> = new Subject();
  private unsubscriber: Subject<any> = new Subject();

  firstFormGroup: FormGroup;

  dummyJson: any;

  constructor(private _store: Store<IAppState>, private _formBuilder: FormBuilder) {

   }

  ngOnInit() {
    this._store.pipe(
      takeUntil(this.unsubscriber),
      select(selectQuestionnaireJson),
      map(res => res ? res['questionnaires'].q1.data.groups : {}))
      .subscribe(res => {
        this.groups.next(res)
    })
    this._store.dispatch(new GetQuestionnaire());
    
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
  }
  
  ngOnDestroy() {
    this.unsubscriber.next(true);
  };

}

