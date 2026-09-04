import type { BreadcrumbProps, QuickCreateAction, SearchType } from "@/layout/topbar/types/topbar_types.ts";
import type {AllType} from "@/features/popular/constants/all_type.ts";
import {useNavigate} from "react-router-dom";
import {ROUTE_PATHS} from "@/app/router/route.ts";
import {Language} from "@/shared/enum/language.ts";

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

export const LANGUAGE_LABELS: Record<Language, string> = {
    [Language.JA]: "Japanese",
    [Language.KO]: "Korean",
    [Language.ZH]: "Chinese",
    [Language.VI]: "Vietnamese",
    [Language.EN]: "English",
};

export const LANGUAGE_OPTIONS: Language[] = [
    Language.JA,
    Language.KO,
    Language.ZH,
    Language.VI,
    Language.EN,
];

export const useQuickCreateActions = (): QuickCreateAction[] => {
    const navigate = useNavigate();

    return [
        {
            title: "Grammar",
            onClick: () => {
                navigate(ROUTE_PATHS.CREATE);
            },
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
};

export const GRAMMAR_DETAIL_BREADCRUMB: BreadcrumbProps["items"] = [];