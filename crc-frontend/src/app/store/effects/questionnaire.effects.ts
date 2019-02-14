import { Injectable } from "@angular/core";
import { Effect, ofType, Actions } from "@ngrx/effects";
import { from, of } from "rxjs";
import { GetQuestionnaire, EQuestionnaireActions, GetQuestionnaireSuccess } from "../actions/questionnaire.actions";
import { map, switchMap } from "rxjs/operators";
import { CommonPartService } from "app/main/questionnaire/post/common-part/common-part.service";
import { IAppState } from "../state/app.state";
import { Store } from "@ngrx/store";

@Injectable()
export class QuestionnaireEffects {
    
    @Effect()
    getQuestionnaire$ = this._actions$.pipe(
        ofType<GetQuestionnaire>(EQuestionnaireActions.GetQuestionnaire),
        switchMap(() => this._commonPartService.getData()),
        switchMap((questionnaire: any) => of(new GetQuestionnaireSuccess(questionnaire)))
    );

    constructor(
        private _commonPartService: CommonPartService,
        private _actions$: Actions,
        private _store: Store<IAppState>) {

    }
}