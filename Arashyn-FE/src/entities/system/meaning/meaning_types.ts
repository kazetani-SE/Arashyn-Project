import type {Example, ExampleCreateRequest} from "../example/example_types";

export type Meaning = {
    id: string;
    content: string;
    groupKey: number;
    examples: Example[];
};

export type MeaningCreateRequest = {
    content: string;
    isPublic: boolean;
    examples: ExampleCreateRequest[];
}