import type { LearningType } from "@/features/learning/constants/learning_types.ts";
import type { GrammarSummaryView } from "@/entities/system/grammar/grammar_summarise_transform.ts";

export type LearningGrammar = Pick<GrammarSummaryView, "id" | "patterns" | "meanings">;

export type LearningPageProps = {
    type: LearningType;
    deckId: string;
    grammars: LearningGrammar[];
};