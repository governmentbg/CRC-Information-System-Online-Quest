import { IAppState } from "../state/app.state";
import { createSelector } from "@ngrx/store";
import { IQuestionnaireState } from "../state/questionnaire.state";

const selectQuestionnaire = (state: IAppState) => state.questionnaire;

export const selectQuestionnaireJson = createSelector(
    selectQuestionnaire,
    (state: IQuestionnaireState) => state.questionnaire
);