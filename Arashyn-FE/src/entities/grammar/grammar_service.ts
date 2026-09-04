import { api } from "@/lib/api/request.ts";
import type { PageResponse } from "@/lib/api/types.ts";
import type {
    GrammarCreateRequest,
    GrammarCreateResponse,
    GrammarDetailResponse,
    GrammarResponse
} from "@/entities/grammar/grammar_types.ts";

export type GrammarListParams = {
    page?: number;
    size?: number;
    query?: string;
    filters?: string[];
};

const DEFAULT_PAGE = 0;
const DEFAULT_SIZE = 6;

export const grammarService = {
    list: (params: GrammarListParams = {}) => {
        const { page = DEFAULT_PAGE, size = DEFAULT_SIZE, query, filters } = params;

        return api.get<PageResponse<GrammarResponse>>("/public/grammar/item_list/grammar", {
            params: {
                page,
                size,
                q: query || undefined,
                filters: filters && filters.length > 0 ? filters.join(",") : undefined,
            },
        });
    },

    /** GET /public/grammar/:grammarId */
    getDetail: (grammarId: string) =>
        api.get<GrammarDetailResponse>(`/public/grammar/${grammarId}`),

    /** POST /protected/grammar/create */
    create: (grammar: GrammarCreateRequest) =>
        api.post<GrammarCreateResponse>("/protected/grammar/create", grammar),
};