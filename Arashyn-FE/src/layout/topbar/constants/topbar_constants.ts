import type {BreadcrumbProps, QuickCreateAction, SearchType} from "@/layout/topbar/types/topbar_types.ts";
import {ROUTE_PATHS} from "@/app/router/route.ts";

export const SEARCH_TYPES: SearchType[] = [
    "All",
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
        href: ROUTE_PATHS.DEFAULT,
    },
    // {
    //     title: "Discover",
    //     href: ROUTE_PATHS.DISCOVER,
    // },
];