import * as React from "react"
import type { GrammarResponse } from "@/shared/response/GrammarResponse.ts"

function transformGrammar(data: GrammarResponse) {
    const grouped = new Map<number, typeof data.components>()

    for (const component of data.components) {
        const group = grouped.get(component.groupKey) ?? []
        group.push(component)
        grouped.set(component.groupKey, group)
    }

    const groups = Array.from(grouped.entries())
        .sort(([a], [b]) => a - b)
        .map(([groupKey, components]) => ({
            groupKey,
            components: [...components].sort((a, b) => a.order - b.order),
        }))

    const patterns = groups.map(({ groupKey, components }) => ({
        groupKey,
        pattern: components
            .map((component) => component.form ?? component.keyword)
            .filter((value): value is string => Boolean(value))
            .join(" + "),
    }))

    const meanings = data.meanings.map((meaning) => meaning.content)
    const filters = data.filters.map((filter) => filter.name)

    return {
        id: data.id,
        title: data.title,

        groups,
        patterns,

        pattern: patterns.map((item) => item.pattern).join("\n"),

        meanings,
        filters,

        data,
    }
}

export type GrammarView = ReturnType<typeof transformGrammar>

export function useGrammar(data: GrammarResponse): GrammarView {
    return React.useMemo(() => transformGrammar(data), [data])
}

export function useGrammarList(list: GrammarResponse[]): GrammarView[] {
    return React.useMemo(() => list.map(transformGrammar), [list])
}