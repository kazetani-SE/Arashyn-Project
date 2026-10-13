import { userDeckApi } from "@/lib/api/api_client.ts";
import type {
    UserDeckCloneRequest,
    UserDeckCreateRequest,
    UserDeckDuplicateCheckRequest,
    UserDeckUpdateRequest,
} from "@/lib/api/generated";

export const userDeckService = {
    /** GET /user_deck */
    async list() {
        const { data } = await userDeckApi.list2();
        return data;
    },

    /** GET /user_deck/:id */
    async getDetail(id: string) {
        const { data } = await userDeckApi.detail2({ id });
        return data;
    },

    /** GET /user_deck/root */
    async root() {
        const { data } = await userDeckApi.root1();
        return data;
    },

    /** POST /user_deck */
    async create(request: UserDeckCreateRequest) {
        const { data } = await userDeckApi.create2({ userDeckCreateRequest: request });
        return data;
    },

    /** POST /user_deck/clone */
    async clone(request: UserDeckCloneRequest) {
        const { data } = await userDeckApi.clone1({ userDeckCloneRequest: request });
        return data;
    },

    /** POST /user_deck/check_duplicate */
    async checkDuplicate(request: UserDeckDuplicateCheckRequest) {
        const { data } = await userDeckApi.checkDuplicate2({
            userDeckDuplicateCheckRequest: request,
        });
        return data;
    },

    /** GET /user_deck/:deckId/check_duplicate_grammars */
    async checkGrammarDuplicates(deckId: string) {
        const { data } = await userDeckApi.checkGrammarDuplicates({ deckId });
        return data;
    },

    /** PUT /user_deck */
    async update(request: UserDeckUpdateRequest) {
        const { data } = await userDeckApi.update2({ userDeckUpdateRequest: request });
        return data;
    },

    /** DELETE /user_deck/:id */
    async delete(id: string) {
        await userDeckApi.delete2({ id });
    },
};