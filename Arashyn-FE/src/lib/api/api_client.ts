import { apiClient } from "./http_client.ts";
import {
    Configuration,
    DeckPublicControllerApi, FolderPublicControllerApi,
    GrammarProtectedControllerApi,
    GrammarPublicControllerApi, UserDeckControllerApi, UserFolderControllerApi, UserGrammarControllerApi
} from "./generated";

const config = new Configuration();

export const grammarPublicApi = new GrammarPublicControllerApi(config, undefined, apiClient);
export const grammarProtectedApi = new GrammarProtectedControllerApi(config, undefined, apiClient);
export const deckPublicApi = new DeckPublicControllerApi(config, undefined, apiClient);
export const folderPublicApi = new FolderPublicControllerApi(config, undefined, apiClient);

export const userGrammarApi = new UserGrammarControllerApi(config, undefined, apiClient);
export const userDeckApi = new UserDeckControllerApi(config, undefined, apiClient);
export const userFolderApi = new UserFolderControllerApi(config, undefined, apiClient);