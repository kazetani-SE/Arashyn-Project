export type AllType =
    | "grammar"
    | "deck"
    | "folder"
    | "search";

export const BROWSABLE_TYPES = ["grammar", "deck", "folder"] as const satisfies readonly AllType[];