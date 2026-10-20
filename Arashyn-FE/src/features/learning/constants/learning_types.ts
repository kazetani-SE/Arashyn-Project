export const LEARNING_TYPES = {
    FLASHCARD: "flashcard",
    FILL_BLANK: "fill_blank",
    ARRANGE: "arrange",
} as const;

export type LearningType = (typeof LEARNING_TYPES)[keyof typeof LEARNING_TYPES];

export const LEARNING_TYPE_META: Record<LearningType, { label: string; implemented: boolean }> = {
    flashcard: { label: "Flashcard", implemented: true },
    fill_blank: { label: "Fill in the blank", implemented: true },
    arrange: { label: "Arrange", implemented: false },
};

export const FLASHCARD_RATINGS = [
    { key: "AGAIN", label: "Again", passed: false, shortcut: "1", className: "bg-red-900/50 text-red-200 hover:bg-red-900/70 border-red-800" },
    { key: "GOOD",  label: "Good",  passed: true,  shortcut: "2", className: "bg-emerald-900/50 text-emerald-200 hover:bg-emerald-900/70 border-emerald-800" },
] as const;

export type FlashcardRating = (typeof FLASHCARD_RATINGS)[number]["key"];

export const isRatingPassed = (rating?: FlashcardRating) =>
    rating ? FLASHCARD_RATINGS.find((r) => r.key === rating)!.passed : false;