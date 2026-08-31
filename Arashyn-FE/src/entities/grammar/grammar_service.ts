import { api } from "@/lib/api/request.ts";
import type { PageResponse } from "@/lib/api/types.ts";
import type {grammar_response} from "@/entities/grammar/grammar_types.ts";

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

        return api.get<PageResponse<grammar_response>>("/grammar-public/item_list/grammar", {
            params: {
                page,
                size,
                q: query || undefined,
                filters: filters && filters.length > 0 ? filters.join(",") : undefined,
            },
        });
    },
};