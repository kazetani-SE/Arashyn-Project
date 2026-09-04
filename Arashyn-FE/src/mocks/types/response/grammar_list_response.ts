import type {GrammarResponse} from "@/entities/grammar/grammar_types.ts";

export type GrammarListResponse = {
    items: GrammarResponse[];
    page: number;
    size: number;
    totalPages: number;
    totalElements: number;
    hasNext: boolean;
    hasPrevious: boolean;
};