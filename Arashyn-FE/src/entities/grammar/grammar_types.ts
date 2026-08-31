import type {ComponentGroup} from "@/entities/component/component_types.ts";
import type {Note} from "@/entities/note/note_types.ts";
import type {Filter} from "@/entities/filter/filter_stypes.ts";

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

export type grammar_detail_response = {
    id: string;
    title: string;
    language: string;
    isPublic: boolean;
    ownerId: string;
    ownerName: string;
    groups: ComponentGroup[];
    notes: Note[];
    filters: Filter[];
};