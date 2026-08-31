import type {Meaning} from "@/entities/meaning/meaning_types.ts";

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