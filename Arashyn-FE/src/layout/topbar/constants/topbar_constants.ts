import type { BreadcrumbProps, QuickCreateAction, SearchType } from "@/layout/topbar/types/topbar_types.ts";
import type {AllType} from "@/features/popular/constants/all_type.ts";

export const SEARCH_TYPES: SearchType[] = [
    "All",
    "Grammar",
    "Deck",
    "Folder",
];

export const SEARCH_TYPE_TO_ALL_TYPE: Partial<Record<SearchType, AllType>> = {
    Grammar: "grammar",
    Deck: "deck",
    Folder: "folder",
};

export const LANGUAGES = [
    "Japanese",
    "Korean",
    "Chinese",
    "Vietnamese",
    "English",
];

export const QUICK_CREATE_ACTIONS: QuickCreateAction[] = [
    {
        title: "Grammar",
        onClick: () => {},
    },
    {
        title: "Component",
        onClick: () => {},
    },
    {
        title: "Vocabulary",
        onClick: () => {},
    },
    {
        title: "Sentence",
        onClick: () => {},
    },
];

export const GRAMMAR_DETAIL_BREADCRUMB: BreadcrumbProps["items"] = [];