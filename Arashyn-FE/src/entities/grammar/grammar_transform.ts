import type { GrammarDetailResponse } from "@/lib/api/generated";

export function transformGrammar(data: GrammarDetailResponse) {
    const groups = [...(data.groups ?? [])]
        .sort((a, b) => a.groupKey - b.groupKey)
        .map((group) => ({
            groupKey: group.groupKey,
            components: [...group.components].sort((a, b) => a.order - b.order),
            meanings: group.meanings ?? [],
        }));

    const patterns = groups.map(({ groupKey, components }) => ({
        groupKey,
        pattern: components
            .map((component) => component.formId ?? component.keyword)
            .filter((value): value is string => Boolean(value))
            .join(" + "),
    }));

    const meanings = groups.flatMap((group) =>
        group.meanings.map((meaning) => meaning.content),
    );
    const filters = (data.filters ?? [])
        .map((filter) => filter.name)
        .filter((name): name is string => Boolean(name));

    return {
        id: data.id ?? "",
        title: data.title,
        groups,
        patterns,
        pattern: patterns.map((item) => item.pattern).join("\n"),
        meanings,
        filters,
        data,
    };
}

export type GrammarView = ReturnType<typeof transformGrammar>;