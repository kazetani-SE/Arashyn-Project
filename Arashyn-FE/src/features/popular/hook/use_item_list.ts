import { useQuery } from "@tanstack/react-query";
import {type BrowsableType} from "@/features/popular/constants/all_type.ts";
import {grammarService} from "@/entities/grammar/grammar_service.ts";

const DEFAULT_PAGE = 0;
const DEFAULT_SIZE = 6;

type UseItemListParams = {
    type: BrowsableType;
    page?: number;
    size?: number;
    query?: string | null;
    filters?: string[];
};

const ACTIVE_TYPES: readonly BrowsableType[] = ["grammar"];

export function useItemList({
                                type,
                                page = DEFAULT_PAGE,
                                size = DEFAULT_SIZE,
                                query,
                                filters,
                            }: UseItemListParams) {
    return useQuery({
        queryKey: ["items", type, page, size, query, filters],
        queryFn: () => {
            switch (type) {
                case "grammar":
                    return grammarService.list({
                        page,
                        size,
                        query: query ?? undefined,
                        filters,
                    });

                // case "deck":
                //     return deckService.list({ page, size, query: query ?? undefined, filters });

                // case "folder":
                //     return folderService.list({ page, size, query: query ?? undefined, filters });

                default:
                    throw new Error(`"${type}" is not wired up to an endpoint yet.`);
            }
        },
        enabled: ACTIVE_TYPES.includes(type),
    });
}