import type {ExampleCreateRequest} from "@/entities/example/example_types.ts";
import type {MeaningCreateRequest} from "@/entities/meaning/meaning_types.ts";
import type {ComponentCreateRequest} from "@/entities/component/component_types.ts";
import type {GrammarCreateRequest, GroupCreateRequest} from "@/entities/grammar/grammar_types.ts";
import type {NoteCreateRequest} from "@/entities/note/note_types.ts";

export type ExampleFormValue = ExampleCreateRequest

export type MeaningFormValue = MeaningCreateRequest

export type ComponentFormValue = ComponentCreateRequest

export type GroupFormValue = GroupCreateRequest

export type NoteFormValue = NoteCreateRequest

export type GrammarFormValues = GrammarCreateRequest

export function emptyExample(): ExampleFormValue {
    return { sentence: "", translation: "", note: "", isPublic: true }
}

export function emptyMeaning(): MeaningFormValue {
    return { content: "", isPublic: true, examples: [] }
}

export function emptyComponent(order: number): ComponentFormValue {
    return { order, formId: "", keyword: "", optional: false }
}

export function emptyGroup(groupKey: number): GroupFormValue {
    return {
        groupKey,
        components: [emptyComponent(1)],
        meanings: [emptyMeaning()],
    }
}

export function emptyGrammarFormValues(): GrammarFormValues {
    return {
        title: "",
        language: "VI",
        isPublic: true,
        groups: [emptyGroup(1)],
        notes: [],
        filterIds: [],
    }
}