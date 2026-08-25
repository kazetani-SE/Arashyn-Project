import { http } from "msw";
import type { grammar_response } from "@/shared/responses/grammar_response.ts";
import {grammar_data, grammarList} from "@/shared/constant/grammar_data.ts";
import type {GrammarListResponse} from "@/mocks/types/response/grammar_list_response.ts";
import {mockError, mockSuccess} from "@/mocks/utils.ts";

const BASE_URL = import.meta.env.VITE_ARASHYN_API_BASE_URL ?? "http://localhost:8080";

// Combine the single detailed mock with the list mocks into one dataset,
// de-duped by id, so both the list and detail endpoints share the same source.
const ALL_GRAMMARS: grammar_response[] = [
    grammar_data,
    ...grammarList.filter((g) => g.id !== grammar_data.id),
];

export const grammar_handler = [
    // GET /grammar-public/item_list/grammar?page=0&size=20&q=xxx&filters=N3,N2
    http.get(`${BASE_URL}/grammar-public/item_list/grammar`, async ({ request }) => {
        const url = new URL(request.url);

        const page = Number(url.searchParams.get("page") ?? 0);
        const size = Number(url.searchParams.get("size") ?? 20);
        const query = url.searchParams.get("q")?.trim().toLowerCase();
        const filterParam = url.searchParams.get("filters"); // e.g. "N3,N2"
        const activeFilters = filterParam
            ? filterParam.split(",").map((f) => f.trim().toLowerCase())
            : [];

        let filtered = ALL_GRAMMARS;

        if (query) {
            filtered = filtered.filter(
                (g) =>
                    g.title.toLowerCase().includes(query) ||
                    g.meanings.some((m) => m.content.toLowerCase().includes(query)),
            );
        }

        if (activeFilters.length > 0) {
            filtered = filtered.filter((g) =>
                g.filters.some((f) => activeFilters.includes(f.name.toLowerCase())),
            );
        }

        const totalElements = filtered.length;
        const totalPages = Math.max(1, Math.ceil(totalElements / size));
        const start = page * size;
        const paginated = filtered.slice(start, start + size);

        const body: GrammarListResponse = {
            items: paginated,
            page,
            size,
            totalPages,
            totalElements,
            hasNext: page < totalPages - 1,
            hasPrevious: page > 0,
        };

        return mockSuccess(body, "Success", { delayMs: "realistic" });
    }),

    // GET /grammar-public/:grammarId
    http.get(`${BASE_URL}/grammar-public/:grammarId`, async ({ params }) => {
        const { grammarId } = params;
        const found = ALL_GRAMMARS.find((g) => g.id === grammarId);

        if (!found) {
            return mockError("Grammar not found", 404, {
                code: "GRAMMAR_NOT_FOUND",
                path: `/grammar-public/${grammarId}`,
            });
        }

        return mockSuccess<grammar_response>(found, "Success", { delayMs: "realistic" });
    }),
];