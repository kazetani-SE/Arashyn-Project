export type Example = {
    id: string;
    sentence: string;
    translation: string;
    note?: string;
};

export type Meaning = {
    id: string;
    content: string;
    groupKey: number;
    examples: Example[];
};

export type Component = {
    id: string;
    order: number;
    keyword: string | null;
    form: string | null;
    groupKey: number;
    optional: boolean;
};

export type ComponentGroup = {
    groupKey: number;
    components: Component[];
    meanings: Meaning[];
};

export type Note = {
    id: string;
    content: string;
};

export type Filter = {
    id: string;
    name: string;
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