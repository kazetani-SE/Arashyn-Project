export type Note = {
    id: string;
    content: string;
};

export type NoteCreateRequest = {
    content: string;
    isPublic: boolean;
    groupKey: number;
}