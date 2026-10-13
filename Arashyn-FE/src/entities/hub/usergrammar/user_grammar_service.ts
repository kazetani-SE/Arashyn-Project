import { userGrammarApi } from "@/lib/api/api_client.ts";
import type {
    UserGrammarCreateRequest,
    UserGrammarDuplicateCheckMultipleRequest,
    UserGrammarDuplicateCheckRequest,
    UserGrammarUpdateRequest,
} from "@/lib/api/generated";

export const userGrammarService = {
    /** GET /user_grammar */
    async list() {
        const { data } = await userGrammarApi.list();
        return data;
    },

    /** GET /user_grammar/:id */
    async getDetail(id: string) {
        const { data } = await userGrammarApi.detail({ id });
        return data;
    },

    /** POST /user_grammar */
    async create(request: UserGrammarCreateRequest) {
        const { data } = await userGrammarApi.create({ userGrammarCreateRequest: request });
        return data;
    },

    /** POST /user_grammar/check_duplicate */
    async checkDuplicate(request: UserGrammarDuplicateCheckRequest) {
        const { data } = await userGrammarApi.checkDuplicate({
            userGrammarDuplicateCheckRequest: request,
        });
        return data;
    },

    /** POST /user_grammar/check_duplicate_multiple */
    async checkDuplicateMultiple(request: UserGrammarDuplicateCheckMultipleRequest) {
        const { data } = await userGrammarApi.checkDuplicateMultiple({
            userGrammarDuplicateCheckMultipleRequest: request,
        });
        return data;
    },

    /** PUT /user_grammar */
    async update(request: UserGrammarUpdateRequest) {
        const { data } = await userGrammarApi.update({ userGrammarUpdateRequest: request });
        return data;
    },

    /** DELETE /user_grammar/:id */
    async delete(id: string) {
        await userGrammarApi._delete({ id });
    },
};