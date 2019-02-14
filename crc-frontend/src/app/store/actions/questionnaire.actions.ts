import { Action } from '@ngrx/store';

export enum EQuestionnaireActions {
    GetQuestionnaire = '[Questionnaire] Get Questionnaire',
    GetQuestionnaireSuccess = '[Questionnaire] Get Questionnaire Success',
    SaveQuestionnaire = '[Questionnaire] Save Questionnaire',
    SaveQuestionnaireSuccess = '[Questionnaire] Save Questionnaire Success',
    SaveQuestionnaireError = '[Questionnaire] Save Questionnaire Error'
}

export class GetQuestionnaire implements Action {
    public readonly type = EQuestionnaireActions.GetQuestionnaire;
}

export class GetQuestionnaireSuccess implements Action {
    public readonly type = EQuestionnaireActions.GetQuestionnaireSuccess;
    constructor(public payload: any) {}
}

export class SaveQuestionnaire implements Action {
    public readonly type = EQuestionnaireActions.SaveQuestionnaire;
    constructor(public payload: {}){}
}

export class SaveQuestionnaireSuccess implements Action {
    public readonly type = EQuestionnaireActions.SaveQuestionnaireSuccess;
    constructor(public payload: {}){}
}

export class SaveQuestionnaireError implements Action {
    public readonly type = EQuestionnaireActions.SaveQuestionnaireError;
    constructor(public payload: string){}
}

export type QuestionnaireActions = 
    GetQuestionnaire | 
    GetQuestionnaireSuccess |
    SaveQuestionnaire |
    SaveQuestionnaireSuccess |
    SaveQuestionnaireError; 