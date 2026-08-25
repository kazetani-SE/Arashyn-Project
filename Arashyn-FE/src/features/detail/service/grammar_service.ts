import { api } from "@/lib/api/request.ts";
import type { grammar_detail_response } from "@/shared/responses/grammar_detail_response.ts";

export const grammarService = {

    detail: (grammarId: string) => {
        return api.get<grammar_detail_response>(`/grammar-public/${grammarId}`);
    },
};