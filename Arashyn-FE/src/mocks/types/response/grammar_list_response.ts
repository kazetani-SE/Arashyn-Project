import type { grammar_response } from "@/shared/responses/grammar_response.ts";

export type GrammarListResponse = {
    grammars: grammar_response[];
    page: number;
    size: number;
    totalPages: number;
    totalElements: number;
    hasNext: boolean;
    hasPrevious: boolean;
};