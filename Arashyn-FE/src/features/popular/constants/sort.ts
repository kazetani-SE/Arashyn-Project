import type {AllType} from "@/features/popular/constants/all_type.ts";
import type {SortOption} from "@/features/popular/types/domains.ts";

export const SORT_OPTIONS: Record<AllType, SortOption[]> = {
    search: [
        { label: "Most relevant", value: "relevance" },
        { label: "Newest", value: "newest" },
        { label: "Most popular", value: "popular" },
    ],
    grammar: [
        { label: "Most popular", value: "popular" },
        { label: "Newest", value: "newest" },
        { label: "A-Z", value: "az" },
        { label: "Difficulty", value: "difficulty" },
    ],
    deck: [
        { label: "Most popular", value: "popular" },
        { label: "Newest", value: "newest" },
        { label: "A-Z", value: "az" },
        { label: "Most cards", value: "cardCount" },
    ],
    folder: [
        { label: "Most popular", value: "popular" },
        { label: "Newest", value: "newest" },
        { label: "A-Z", value: "az" },
        { label: "Most items", value: "itemCount" },
    ],
};