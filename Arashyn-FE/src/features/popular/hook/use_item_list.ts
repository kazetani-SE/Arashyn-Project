import { useQuery } from "@tanstack/react-query";
import {type BrowsableType} from "@/features/popular/constants/all_type.ts";
import {grammarService} from "@/features/popular/service/grammar_service.ts";

const DEFAULT_PAGE = 0;
const DEFAULT_SIZE = 6;

type UseItemListParams = {
    type: BrowsableType;
    page?: number;
    size?: number;
    query?: string | null;
    filters?: string[];
};

// Only "grammar" has a real backend + mock handler right now.
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
        queryFn: async () => {
            switch (type) {
                case "grammar":
                    return grammarService.list({
                        page,
                        size,
                        query: query ?? undefined,
                        filters,
                    });

                // case "deck":
                //     return deckService.list({
                //         page,
                //         size,
                //         query: query ?? undefined,
                //         filters,
                //     });

                // case "folder":
                //     return folderService.list({
                //         page,
                //         size,
                //         query: query ?? undefined,
                //         filters,
                //     });

                default:
                    throw new Error(`"${type}" is not wired up to an endpoint yet.`);
            }
        },
        // Prevents deck/folder from firing a request that will just throw
        // once they hit this page, until their services exist.
        enabled: ACTIVE_TYPES.includes(type),
    });
}