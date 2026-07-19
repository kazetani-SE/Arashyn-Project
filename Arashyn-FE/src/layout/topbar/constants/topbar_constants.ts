import type {BreadcrumbProps, QuickCreateAction, SearchType} from "@/layout/topbar/types/topbar_types.ts";

export const SEARCH_TYPES: SearchType[] = [
    "Grammar",
    "Component",
    "Vocabulary",
    "Sentence",
];

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

export const GRAMMAR_DETAIL_BREADCRUMB: BreadcrumbProps["items"] = [
    {
        title: "Home",
        href: "/",
    },
    {
        title: "Grammar",
        href: "/grammar",
    },
    {
        title: "JLPT N5",
        href: "/grammar/jlpt-n5",
    },
];