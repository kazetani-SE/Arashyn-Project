import type {Folder} from "@/layout/sidebar/types/folder.ts";
import {DECKS} from "@/layout/sidebar/constants/decks_constant.ts";

export const FOLDERS: Folder[] = [
    {
        id: "1",
        name: "Programming",
        decks: [DECKS[0], DECKS[1], DECKS[2]],
    },
    {
        id: "2",
        name: "Frontend",
        decks: [DECKS[2], DECKS[3]],
    },
    {
        id: "3",
        name: "Database",
        decks: [DECKS[4]],
    },
];