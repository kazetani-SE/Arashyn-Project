import { grammarPublicApi } from "@/lib/api/api_client.ts";
import type {
    GrammarListRequest,
    GrammarCreateRequest,
} from "@/lib/api/generated";

export type GrammarListParams = {
    page?: number;
    size?: number;
    language?: string;
    sort?: string;
    direction?: string;
};

export type GrammarSearchParams = {
    query?: string;
    filters?: string[];
    forms?: string[];
    isKeyword?: boolean;
    page?: number;
    size?: number;
    language?: string;
};

const DEFAULT_PAGE = 0;
const DEFAULT_SIZE = 6;

const joinList = (list?: string[]) =>
    list && list.length > 0 ? list.join(",") : undefined;

export const grammarPublicService = {
    /** GET /public/grammar/item_list/grammar */
    async list(params: GrammarListParams = {}) {
        const { page = DEFAULT_PAGE, size = DEFAULT_SIZE, ...rest } = params;
        const { data } = await grammarPublicApi.getItems({ page, size, ...rest });
        return data;
    },

    /** GET /public/grammar/search */
    async search(params: GrammarSearchParams = {}) {
        const {
            page = DEFAULT_PAGE,
            size = DEFAULT_SIZE,
            query,
            filters,
            forms,
            isKeyword,
            language,
        } = params;

        const { data } = await grammarPublicApi.search({
            query: query || undefined,
            filters: joinList(filters),
            forms: joinList(forms),
            isKeyword,
            page,
            size,
            language,
        });
        return data;
    },

    /** POST /public/grammar */
    async listByRequest(body: GrammarListRequest, page: number = DEFAULT_PAGE) {
        const { data } = await grammarPublicApi.getPublicGrammars({
            grammarListRequest: body,
            page,
        });
        return data;
    },

    /** GET /public/grammar/:grammarId */
    async getDetail(id: string) {
        const { data } = await grammarPublicApi.getDetail({ grammarId: id });
        return data;
    },

    /** POST /public/grammar/check-exist */
    async checkExist(body: GrammarCreateRequest) {
        const { data } = await grammarPublicApi.checkGrammarExist({ grammarCreateRequest: body });
        return data;
    },

    /** POST /public/grammar/similar */
    async checkSimilar(body: GrammarCreateRequest) {
        const { data } = await grammarPublicApi.checkSimilarGrammar({ grammarCreateRequest: body });
        return data;
    },
};