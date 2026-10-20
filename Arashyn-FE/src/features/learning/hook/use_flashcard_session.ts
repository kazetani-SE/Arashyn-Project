import { useCallback, useMemo, useReducer } from "react";
import { FLASHCARD_RATINGS, isRatingPassed, type FlashcardRating } from "@/features/learning/constants/learning_types.ts";
import type { LearningGrammar } from "@/features/learning/types/learning_types.ts";

type Phase = "studying" | "roundDone" | "completed";

type State = {
    queue: string[];
    index: number;
    flipped: boolean;
    round: number;
    phase: Phase;
    ratings: Record<string, FlashcardRating>;
};

type Action =
    | { type: "FLIP" }
    | { type: "RATE"; rating: FlashcardRating }
    | { type: "NEXT_ROUND" }
    | { type: "RESET"; ids: string[] };

const init = (ids: string[]): State => ({
    queue: ids,
    index: 0,
    flipped: false,
    round: 1,
    phase: "studying",
    ratings: {},
});

function reducer(state: State, action: Action): State {
    switch (action.type) {
        case "FLIP":
            return state.phase === "studying" ? { ...state, flipped: !state.flipped } : state;

        case "RATE": {
            if (state.phase !== "studying") return state;
            const id = state.queue[state.index];
            const ratings = { ...state.ratings, [id]: action.rating };

            if (state.index < state.queue.length - 1) {
                return { ...state, ratings, index: state.index + 1, flipped: false };
            }

            const hasPending = state.queue.some((q) => !isRatingPassed(ratings[q]));
            return { ...state, ratings, flipped: false, phase: hasPending ? "roundDone" : "completed" };
        }

        case "NEXT_ROUND": {
            if (state.phase !== "roundDone") return state;
            return {
                ...state,
                queue: state.queue.filter((id) => !isRatingPassed(state.ratings[id])),
                index: 0,
                round: state.round + 1,
                flipped: false,
                phase: "studying",
            };
        }

        case "RESET":
            return init(action.ids);
    }
}

export function useFlashcardSession(grammars: LearningGrammar[]) {
    const ids = useMemo(() => grammars.map((g) => g.id), [grammars]);
    const [state, dispatch] = useReducer(reducer, ids, init);

    const byId = useMemo(() => new Map(grammars.map((g) => [g.id, g])), [grammars]);
    const current = state.phase === "studying" ? byId.get(state.queue[state.index]) : undefined;

    const passedCount = grammars.filter((g) => isRatingPassed(state.ratings[g.id])).length;

    const ratingCounts = useMemo(() => {
        const counts = Object.fromEntries(FLASHCARD_RATINGS.map((r) => [r.key, 0])) as Record<FlashcardRating, number>;
        Object.values(state.ratings).forEach((r) => counts[r]++);
        return counts;
    }, [state.ratings]);

    return {
        phase: state.phase,
        round: state.round,
        current,
        flipped: state.flipped,
        roundIndex: state.index,
        roundSize: state.queue.length,
        total: grammars.length,
        passedCount,
        remainingCount: grammars.length - passedCount,
        ratingCounts,
        hasProgress: Object.keys(state.ratings).length > 0,
        flip: useCallback(() => dispatch({ type: "FLIP" }), []),
        rate: useCallback((rating: FlashcardRating) => dispatch({ type: "RATE", rating }), []),
        nextRound: useCallback(() => dispatch({ type: "NEXT_ROUND" }), []),
        reset: useCallback(() => dispatch({ type: "RESET", ids }), [ids]),
    };
}