import type {Language} from "@/shared/enum/language.ts";
import {api} from "@/lib/api/request.ts";
import type {ListSystemFilterResponse} from "@/entities/filter/filter_types.ts";

export const filterService = {
    list: (language: Language) =>
        api.get<ListSystemFilterResponse>("/public/system-filters", {
            params: {
                language,
            },
        }),
};