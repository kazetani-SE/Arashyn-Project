import { apiClient } from "./http_client.ts";
import {Configuration, GrammarProtectedControllerApi, GrammarPublicControllerApi} from "./generated";

const config = new Configuration();

export const grammarPublicApi = new GrammarPublicControllerApi(config, undefined, apiClient);
export const grammarProtectedApi = new GrammarProtectedControllerApi(config, undefined, apiClient);