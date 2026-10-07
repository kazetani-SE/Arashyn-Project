import { http } from "msw";
import type { GrammarListResponse } from "@/lib/api/generated";
import { mockError, mockSuccess } from "@/mocks/utils.ts";
import { grammar_detail_data, grammar_list_data } from "@/mocks/constant/grammar_mock_data.ts";

const BASE_URL = import.meta.env.VITE_ARASHYN_API_BASE_URL ?? "http://localhost:8080";

function paginate(page: number, size: number, query?: string, filters: string[] = []) {
    let filtered = grammar_list_data;

    if (query) {
        filtered = filtered.filter(
            (g) =>
                g.title?.toLowerCase().includes(query) ||
                g.meanings?.some((m) => m.content?.toLowerCase().includes(query)),
        );
    }

    if (filters.length > 0) {
        filtered = filtered.filter((g) =>
            g.filters?.some((f) => filters.includes(f.name?.toLowerCase() ?? "")),
        );
    }

    const totalElements = filtered.length;
    const totalPages = Math.max(1, Math.ceil(totalElements / size));

    const body: GrammarListResponse = {
        items: filtered.slice(page * size, page * size + size),
        page,
        size,
        totalPages,
        totalElements,
        hasNext: page < totalPages - 1,
        hasPrevious: page > 0,
    };
    return body;
}

export const grammar_handler = [
    // GET /public/grammar/item_list/grammar?page&size
    http.get(`${BASE_URL}/public/grammar/item_list/grammar`, async ({ request }) => {
        const p = new URL(request.url).searchParams;
        const body = paginate(Number(p.get("page") ?? 0), Number(p.get("size") ?? 20));
        return mockSuccess(body, "Success", { delayMs: "realistic" });
    }),

    // GET /public/grammar/search?query&filters&page&size  (phải khai báo trước :grammarId)
    http.get(`${BASE_URL}/public/grammar/search`, async ({ request }) => {
        const p = new URL(request.url).searchParams;
        const filters = (p.get("filters") ?? "")
            .split(",")
            .map((f) => f.trim().toLowerCase())
            .filter(Boolean);
        const body = paginate(
            Number(p.get("page") ?? 0),
            Number(p.get("size") ?? 20),
            p.get("query")?.trim().toLowerCase(),
            filters,
        );
        return mockSuccess(body, "Success", { delayMs: "realistic" });
    }),

    // GET /public/grammar/:grammarId
    http.get(`${BASE_URL}/public/grammar/:grammarId`, async ({ params }) => {
        const found = grammar_detail_data.find((g) => g.id === params.grammarId);

        if (!found) {
            return mockError("Grammar not found", 404, {
                code: "GRAMMAR_NOT_FOUND",
                path: `/public/grammar/${params.grammarId}`,
            });
        }

        return mockSuccess(found, "Success", { delayMs: "realistic" });
    }),
];