import { apiClient } from "@/lib/api/client.ts"; // adjust to your actual axios/fetch instance
import type { ApiResponse } from "@/lib/api/types.ts";
import type { GrammarListResponse } from "@/mocks/types/response/grammar_list_response.ts";

export type GrammarListParams = {
    page?: number;
    size?: number;
    query?: string;
    filters?: string[];
};

const DEFAULT_PAGE = 0;
const DEFAULT_SIZE = 6;

export const grammarService = {
    list: async (params: GrammarListParams = {}): Promise<GrammarListResponse> => {
        const { page = DEFAULT_PAGE, size = DEFAULT_SIZE, query, filters } = params;

        const response = await apiClient.get<ApiResponse<GrammarListResponse>>(
            "/api/grammars",
            {
                params: {
                    page,
                    size,
                    q: query || undefined,
                    filters: filters && filters.length > 0 ? filters.join(",") : undefined,
                },
            },
        );

        return response.data.data;
    },
};