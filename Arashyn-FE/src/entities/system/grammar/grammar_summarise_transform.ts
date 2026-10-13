import type {GrammarComponentSummaryResponse, GrammarSummaryResponse} from "@/lib/api/generated";

export function transformGrammarSummary(data: GrammarSummaryResponse) {
    const grouped = new Map<number, GrammarComponentSummaryResponse[]>();

    for (const component of data.components ?? []) {
        const key = component.groupKey ?? 0;
        grouped.set(key, [...(grouped.get(key) ?? []), component]);
    }

    const patterns = Array.from(grouped.entries())
        .sort(([a], [b]) => a - b)
        .map(([groupKey, components]) => ({
            groupKey,
            pattern: [...components]
                .sort((a, b) => (a.order ?? 0) - (b.order ?? 0))
                .map((c) => c.form ?? c.keyword)
                .filter((v): v is string => Boolean(v))
                .join(" + "),
        }));

    const meanings = (data.meanings ?? [])
        .map((m) => m.content)
        .filter((c): c is string => Boolean(c));

    const filters = (data.filters ?? [])
        .map((f) => f.name)
        .filter((n): n is string => Boolean(n));

    return {
        id: data.id ?? "",
        title: data.title ?? "",
        patterns,
        meanings,
        filters,
    };
}

export type GrammarSummaryView = ReturnType<typeof transformGrammarSummary>;