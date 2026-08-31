import type { Example } from "../example/example_types";

export type Meaning = {
    id: string;
    content: string;
    groupKey: number;
    examples: Example[];
};