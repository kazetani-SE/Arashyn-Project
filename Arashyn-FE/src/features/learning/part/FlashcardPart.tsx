import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FLASHCARD_RATINGS } from "@/features/learning/constants/learning_types.ts";
import { useFlashcardSession } from "@/features/learning/hook/use_flashcard_session.ts";
import { useLeaveGuard } from "@/features/learning/hook/use_leave_guard.ts";
import { FlashcardCard } from "@/features/learning/components/FlashcardCard.tsx";
import { FlashcardRatingBar } from "@/features/learning/components/FlashcardRatingBar.tsx";
import { FlashcardRoundSummary } from "@/features/learning/components/FlashcardRoundSummary.tsx";
import { LeaveConfirmDialog } from "@/features/learning/components/LeaveConfirmDialog.tsx";
import type { LearningGrammar } from "@/features/learning/types/learning_types.ts";

type Props = { grammars: LearningGrammar[] };

export function FlashcardPart({ grammars }: Props) {
    const navigate = useNavigate();
    const s = useFlashcardSession(grammars);

    const { isBlocked, confirmLeave, cancelLeave } = useLeaveGuard(s.hasProgress && s.phase !== "completed");

    useEffect(() => {
        if (s.phase !== "studying") return;
        const onKey = (e: KeyboardEvent) => {
            if (e.code === "Space") {
                e.preventDefault();
                s.flip();
                return;
            }
            const rating = FLASHCARD_RATINGS.find((r) => r.shortcut === e.key);
            if (rating && s.flipped) s.rate(rating.key);
        };
        window.addEventListener("keydown", onKey);
        return () => window.removeEventListener("keydown", onKey);
    }, [s.phase, s.flipped, s.flip, s.rate]);

    if (grammars.length === 0) {
        return <p className="py-8 text-center text-muted-foreground">This deck has no grammar to learn yet.</p>;
    }

    const progressPct = (s.passedCount / s.total) * 100;

    return (
        <div className="mx-auto flex w-full max-w-2xl flex-col gap-4">
            {/* Progress */}
            <div className="space-y-1">
                <div className="flex justify-between text-xs text-muted-foreground">
                    <span>Round {s.round}</span>
                    <span>
                        {s.phase === "studying" ? `${s.roundIndex + 1}/${s.roundSize} · ` : ""}
                        {s.passedCount}/{s.total} passed
                    </span>
                </div>
                <div className="h-1.5 overflow-hidden rounded-full bg-muted">
                    <div className="h-full bg-[#818cf8] transition-all" style={{ width: `${progressPct}%` }} />
                </div>
            </div>

            {s.phase === "studying" && s.current ? (
                <>
                    <FlashcardCard grammar={s.current} flipped={s.flipped} onFlip={s.flip} />
                    {s.flipped && <FlashcardRatingBar onRate={s.rate} />}
                </>
            ) : (
                <FlashcardRoundSummary
                    completed={s.phase === "completed"}
                    round={s.round}
                    passedCount={s.passedCount}
                    total={s.total}
                    remainingCount={s.remainingCount}
                    ratingCounts={s.ratingCounts}
                    onNextRound={s.nextRound}
                    onRestart={s.reset}
                    onFinish={() => navigate(-1)}
                />
            )}

            <LeaveConfirmDialog open={isBlocked} onStay={cancelLeave} onLeave={confirmLeave} />
        </div>
    );
}