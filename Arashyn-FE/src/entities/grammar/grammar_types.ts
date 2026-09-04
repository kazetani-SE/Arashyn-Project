import type {ComponentCreateRequest, ComponentGroup} from "@/entities/component/component_types.ts";
import type {NoteCreateRequest, Note} from "@/entities/note/note_types.ts";
import type {Filter} from "@/entities/filter/filter_types.ts";
import type {MeaningCreateRequest} from "@/entities/meaning/meaning_types.ts";
import type {Language} from "@/shared/enum/language.ts";

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

export type GrammarResponse = {
    id: string
    title: string
    components: GrammarComponent[]
    meanings: GrammarMeaning[]
    filters: GrammarFilter[]
}

export type GrammarDetailResponse = {
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

export type GroupCreateRequest = {
    groupKey: number;
    components: ComponentCreateRequest[];
    meanings: MeaningCreateRequest[];
}

export type GrammarCreateRequest = {
    title: string;
    language: Language;
    isPublic: boolean;
    groups: GroupCreateRequest[];
    notes: NoteCreateRequest[];
    filterIds: string[];
}

export type GrammarCreateResponse = {
    id: string;
}