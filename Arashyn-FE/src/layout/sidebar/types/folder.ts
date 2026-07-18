import type {Deck} from "@/layout/sidebar/types/deck.ts";

export type Folder = {
    id: string,
    name: string,
    decks: Deck[],
}