import * as React from "react"
import type { detail_response } from "@/shared/responses/detail_response.ts"

function transformDetail(data: detail_response) {
    // Sắp xếp groups theo groupKey, components trong mỗi group theo order
    const groups = [...data.groups]
        .sort((a, b) => a.groupKey - b.groupKey)
        .map((group) => {
            const components = [...group.components].sort((a, b) => a.order - b.order)

            const pattern = components
                .map((c) => c.form ?? c.keyword)
                .filter((v): v is string => Boolean(v))
                .join(" + ")

            const meanings = group.meanings.map((meaning) => ({
                id: meaning.id,
                content: meaning.content,
                examples: meaning.examples,
            }))

            return {
                groupKey: group.groupKey,
                components,
                pattern,
                meanings,
            }
        })

    const patterns = groups.map(({ groupKey, pattern }) => ({ groupKey, pattern }))
    const pattern = patterns.map((p) => p.pattern).join("\n")

    // Gom phẳng để dễ render list mà không cần lặp qua groups
    const allMeanings = groups.flatMap((g) => g.meanings)
    const allExamples = allMeanings.flatMap((m) => m.examples)

    const notes = data.notes.map((n) => n.content)
    const filters = data.filters.map((f) => f.name)

    return {
        id: data.id,
        title: data.title,
        language: data.language,
        isPublic: data.isPublic,

        owner: {
            id: data.ownerId,
            name: data.ownerName,
        },

        groups,
        patterns,
        pattern,

        meanings: allMeanings,
        examples: allExamples,

        notes,
        filters,

        data,
    }
}

export type DetailView = ReturnType<typeof transformDetail>

export function useDetail(data: detail_response): DetailView {
    return React.useMemo(() => transformDetail(data), [data])
}

export function useDetailList(list: detail_response[]): DetailView[] {
    return React.useMemo(() => list.map(transformDetail), [list])
}