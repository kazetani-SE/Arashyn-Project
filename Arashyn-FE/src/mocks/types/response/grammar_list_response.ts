import type {grammar_response} from "@/entities/grammar/grammar_types.ts";

export type GrammarListResponse = {
    items: grammar_response[];
    page: number;
    size: number;
    totalPages: number;
    totalElements: number;
    hasNext: boolean;
    hasPrevious: boolean;
};