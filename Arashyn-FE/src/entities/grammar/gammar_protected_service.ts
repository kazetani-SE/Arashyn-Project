import { grammarProtectedApi } from "@/lib/api/api_client.ts";
import type {
    AssignFilterRequest,
    GrammarCreateMultipleRequest,
    GrammarCreateRequest,
    GrammarExtendRequest,
    GrammarUpdateRequest,
} from "@/lib/api/generated";

export const grammarProtectedService = {
    /** POST /protected/grammar/create */
    async create(body: GrammarCreateRequest) {
        const { data } = await grammarProtectedApi.create5({ grammarCreateRequest: body });
        return data; // GrammarCreateResponse
    },

    /** POST /protected/grammar/create_multiple */
    async createMultiple(body: GrammarCreateMultipleRequest) {
        await grammarProtectedApi.createMultiple({ grammarCreateMultipleRequest: body });
    },

    /** GET /protected/grammar/edit/:grammarId */
    async getUpdateDetail(grammarId: string) {
        const { data } = await grammarProtectedApi.getUpdateDetail({ grammarId });
        return data; // GrammarEditResponse
    },

    /** POST /protected/grammar/update */
    async update(body: GrammarUpdateRequest) {
        await grammarProtectedApi.update4({ grammarUpdateRequest: body });
    },

    /** POST /protected/grammar/:grammarId/extend */
    async extend(grammarId: string, body: GrammarExtendRequest) {
        await grammarProtectedApi.extendGrammar({ grammarId, grammarExtendRequest: body });
    },

    /** POST /protected/grammar/:grammarId/filters */
    async assignFilters(grammarId: string, body: AssignFilterRequest) {
        await grammarProtectedApi.assignFilters({ grammarId, assignFilterRequest: body });
    },

    /** DELETE /protected/grammar/:grammarId */
    async remove(grammarId: string) {
        await grammarProtectedApi.deleteGrammar({ grammarId });
    },

    /** POST /protected/grammar/:grammarId/restore */
    async restore(grammarId: string) {
        await grammarProtectedApi.restoreGrammar({ grammarId });
    },
};