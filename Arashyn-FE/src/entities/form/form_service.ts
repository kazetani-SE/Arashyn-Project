import { api } from "@/lib/api/request.ts";
import { Language } from "@/shared/enum/language.ts";
import type {ListFormResponse} from "@/entities/form/form_types.ts";

export const formService = {
    list: (language: Language) =>
        api.get<ListFormResponse>("/public/forms", {
            params: {
                language,
            },
        }),
};