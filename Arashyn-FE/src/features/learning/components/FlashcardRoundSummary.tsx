import { FLASHCARD_RATINGS, type FlashcardRating } from "@/features/learning/constants/learning_types.ts";
import { Button } from "@/components/ui/button";

type Props = {
    completed: boolean;
    round: number;
    passedCount: number;
    total: number;
    remainingCount: number;
    ratingCounts: Record<FlashcardRating, number>;
    onNextRound: () => void;
    onRestart: () => void;
    onFinish: () => void;
};

export function FlashcardRoundSummary(p: Props) {
    return (
        <div className="flex flex-col items-center gap-6 rounded-xl bg-card p-8 ring-1 ring-foreground/10">
            <h2 className="text-2xl font-semibold">
                {p.completed ? "All cards passed 🎉" : `Round ${p.round} finished`}
            </h2>
            <p className="text-muted-foreground">
                {p.passedCount}/{p.total} passed
                {!p.completed && ` · ${p.remainingCount} left to review`}
            </p>

            <div className="flex flex-wrap justify-center gap-2">
                {FLASHCARD_RATINGS.map((r) => (
                    <span key={r.key} className={`rounded-full border px-3 py-1 text-xs ${r.className}`}>
                        {r.label}: {p.ratingCounts[r.key]}
                    </span>
                ))}
            </div>

            <div className="flex gap-2">
                {p.completed ? (
                    <>
                        <Button variant="outline" onClick={p.onRestart}>Study again</Button>
                        <Button onClick={p.onFinish}>Finish</Button>
                    </>
                ) : (
                    <Button onClick={p.onNextRound}>Review {p.remainingCount} remaining</Button>
                )}
            </div>
        </div>
    );
}