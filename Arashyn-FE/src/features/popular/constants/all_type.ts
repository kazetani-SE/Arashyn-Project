export type AllType =
    | "grammar"
    | "deck"
    | "folder"
    | "search";

export const BROWSABLE_TYPES = ["grammar", "deck", "folder"] as const satisfies readonly AllType[];

export type BrowsableType = typeof BROWSABLE_TYPES[number];

export function isBrowsableType(type: AllType): type is BrowsableType {
    return BROWSABLE_TYPES.includes(type as BrowsableType);
}