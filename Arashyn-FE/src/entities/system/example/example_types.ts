export type Example = {
    id: string;
    sentence: string;
    translation: string;
    note?: string;
};

export type ExampleCreateRequest = {
    sentence: string;
    translation: string;
    note?: string;
    isPublic: boolean;
}