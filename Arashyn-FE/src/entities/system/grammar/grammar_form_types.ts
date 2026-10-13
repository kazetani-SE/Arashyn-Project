import {
    GrammarCreateRequestLanguageEnum,
    type ComponentCreateRequest,
    type GrammarCreateRequest,
    type Group,
    type MeaningCreateBase,
    type NoteCreateRequest,
} from "@/lib/api/generated";

export type GrammarFormValues = GrammarCreateRequest;
export type GroupFormValue = Group;
export type ComponentFormValue = ComponentCreateRequest;
export type MeaningFormValue = MeaningCreateBase;
export type NoteFormValue = NoteCreateRequest;
export type ExampleFormValue = NonNullable<MeaningFormValue["examples"]>[number];

export function emptyExample(): ExampleFormValue {
    return { sentence: "", translation: "", note: "", isPublic: true };
}

export function emptyMeaning(): MeaningFormValue {
    return { content: "", isPublic: true, examples: [] };
}

export function emptyComponent(order: number): ComponentFormValue {
    return { order, formId: "", keyword: "", optional: false };
}

export function emptyGroup(groupKey: number): GroupFormValue {
    return {
        groupKey,
        components: [emptyComponent(1)],
        meanings: [emptyMeaning()],
    };
}

export function emptyGrammarFormValues(): GrammarFormValues {
    return {
        title: "",
        language: GrammarCreateRequestLanguageEnum.Vi,
        isPublic: true,
        groups: [emptyGroup(1)],
        notes: [],
        filterIds: [],
    };
}