import { ActionReducerMap } from "@ngrx/store";
import { IAppState } from "../state/app.state";
import { questionnaireReducers } from "./questionnaire.reducers";

export const appReducers: ActionReducerMap<IAppState, any> = {
    questionnaire: questionnaireReducers
}