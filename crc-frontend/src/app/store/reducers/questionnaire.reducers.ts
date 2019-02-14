import { IQuestionnaireState, initialQuestionnaireState } from "../state/questionnaire.state";
import { QuestionnaireActions, EQuestionnaireActions } from "../actions/questionnaire.actions";

export const questionnaireReducers = (
    state = initialQuestionnaireState,
    action: QuestionnaireActions
): IQuestionnaireState => {
    switch (action.type) {
        case EQuestionnaireActions.GetQuestionnaireSuccess: {
            return {
                ...state,
                questionnaire: action.payload
            };
        }
        default:
            return state;
    }
}