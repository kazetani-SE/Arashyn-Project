import * as React from "react"
import type {GrammarDetailResponse, GrammarSummaryResponse} from "@/lib/api/generated"
import { transformGrammar, type GrammarView } from "@/entities/grammar/grammar_transform.ts"
import {type GrammarSummaryView, transformGrammarSummary} from "@/entities/grammar/grammar_summarise_transform.ts";

export type { GrammarView }

export function useGrammar(data: GrammarDetailResponse): GrammarView {
    return React.useMemo(() => transformGrammar(data), [data])
}

export function useGrammarList(list: GrammarDetailResponse[]): GrammarView[] {
    return React.useMemo(() => list.map(transformGrammar), [list])
}

export function useGrammarSummaryList(list: GrammarSummaryResponse[]): GrammarSummaryView[] {
    return React.useMemo(() => list.map(transformGrammarSummary), [list])
}