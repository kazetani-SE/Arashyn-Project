import { useQuery } from "@tanstack/react-query";
import {type BrowsableType} from "@/features/popular/constants/all_type.ts";
import {grammarPublicService} from "@/entities/grammar/grammar_public_service.ts"
import {deckPublicService} from "@/entities/deck/deck_public_service.ts";
import {folderPublicService} from "@/entities/folder/folder_public_service.ts";

const DEFAULT_PAGE = 0;
const DEFAULT_SIZE = 6;

type UseItemListParams = {
    type: BrowsableType;
    page?: number;
    size?: number;
    query?: string | null;
    filters?: string[];
    language?: string;
};

const ACTIVE_TYPES: readonly BrowsableType[] = ["grammar", "deck", "folder"];

export function useItemList({
                                type,
                                page = DEFAULT_PAGE,
                                size = DEFAULT_SIZE,
                                query,
                                filters,
                                language,
                            }: UseItemListParams) {
    return useQuery({
        queryKey: ["items", type, page, size, query, filters, language],
        queryFn: () => {
            switch (type) {
                case "grammar": {
                    const hasSearch = !!query || (filters?.length ?? 0) > 0;

                    return hasSearch
                        ? grammarPublicService.search({
                            page,
                            size,
                            query: query ?? undefined,
                            filters,
                            language,
                        })
                        : grammarPublicService.list({ page, size, language });
                }

                case "deck": {
                    return deckPublicService.list().then((res) => ({
                        items: (res.deckList ?? []).slice(page * size, (page + 1) * size),
                    }));
                }

                case "folder": {
                    return folderPublicService.list().then((res) => ({
                        items: (res.items ?? []).slice(page * size, (page + 1) * size),
                    }));
                }

                default:
                    throw new Error(`"${type}" is not wired up to an endpoint yet.`);
            }
        },
        enabled: ACTIVE_TYPES.includes(type),
    });
}