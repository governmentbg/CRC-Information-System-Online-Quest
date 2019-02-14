import { initialQuestionnaireState, IQuestionnaireState } from "./questionnaire.state";

export interface IAppState {
    questionnaire: IQuestionnaireState
}

export const initialAppState = {
    questionnaire: initialQuestionnaireState
}

export function getInitialState() {
    return initialAppState;
}