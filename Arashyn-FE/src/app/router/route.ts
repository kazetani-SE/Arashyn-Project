import type { AllType } from "@/features/popular/constants/all_type.ts";

export const ROUTE_PATHS = {
    DEFAULT: "/",
    LOGIN: "/login",
    REGISTER: "/register",
    DISCOVER: "/discover",
    DASHBOARD: "/dashboard",
    COMMUNITY: "/community",
    SETTINGS: "/settings",
    CREATE: "/create",
    HUB: "/hub",

    ITEM_LIST: "/item_list/:type",
    SEARCH: "/search",

    DETAIL: "/detail/:grammarId",
    DECK_DETAIL: "/deck/:id",
    FOLDER_DETAIL: "/folder/:id",

    USER_GRAMMAR_DETAIL: "/hub/grammar/:id",
    USER_DECK_DETAIL: "/hub/deck/:id",
    USER_FOLDER_DETAIL: "/hub/folder/:id",

    LEARNING: "/learning/:deckId/:type",
} as const;

export const ROUTES = {
    itemList: (
        type: AllType,
        params?: { page?: number; sort?: string; direction?: "asc" | "desc" },
    ) => {
        const searchParams = new URLSearchParams();
        if (params?.page) searchParams.set("page", String(params.page));
        if (params?.sort) searchParams.set("sort", params.sort);
        if (params?.direction) searchParams.set("direction", String(params.direction));
        const qs = searchParams.toString();
        return `/item_list/${type}${qs ? `?${qs}` : ""}`;
    },

    search: (params: {
        query: string;
        type?: AllType;
        page?: number;
        sort?: string;
        direction?: "asc" | "desc";
        filters?: string[];
    }) => {
        const searchParams = new URLSearchParams();
        searchParams.set("type", "search");
        searchParams.set("query", params.query);
        if (params.page) searchParams.set("page", String(params.page));
        if (params.sort) searchParams.set("sort", params.sort);
        if (params.direction) searchParams.set("direction", String(params.direction));
        if (params.filters?.length) searchParams.set("filters", params.filters.join(","));
        return `/search?${searchParams.toString()}`;
    },

    grammarDetail: (grammarId: string) => `/detail/${grammarId}`,
    deckDetail: (deckId: string) => `/deck/${deckId}`,
    folderDetail: (folderId: string) => `/folder/${folderId}`,

    userGrammarDetail: (id: string) => `/hub/grammar/${id}`,
    userDeckDetail: (id: string) => `/hub/deck/${id}`,
    userFolderDetail: (id: string) => `/hub/folder/${id}`,

    learning: (deckId: string, type: string) => `/learning/${deckId}/${type}`,
} as const;