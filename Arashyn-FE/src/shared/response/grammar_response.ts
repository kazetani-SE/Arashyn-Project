export type GrammarComponent = {
    groupKey: number;
    order: number
    keyword: string | null
    form: string | null
}

export type GrammarMeaning = {
    content: string
}

export type GrammarFilter = {
    name: string
}

export type grammar_response = {
    id: string
    title: string
    components: GrammarComponent[]
    meanings: GrammarMeaning[]
    filters: GrammarFilter[]
}