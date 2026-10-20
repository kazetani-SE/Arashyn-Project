import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { ROUTE_PATHS } from "@/app/router/route.ts";
import { useFillBlankSession } from "@/features/learning/hook/use_fill_blank_session.ts";
import { useLeaveGuard } from "@/features/learning/hook/use_leave_guard.ts";
import { FillBlankSettingDialog } from "@/features/learning/components/FillBlankSettingDialog.tsx";
import { FillBlankQuestionCard } from "@/features/learning/components/FillBlankQuestionCard.tsx";
import { LeaveConfirmDialog } from "@/features/learning/components/LeaveConfirmDialog.tsx";

type Props = { deckId: string };

export function FillBlankPart({ deckId }: Props) {
    const navigate = useNavigate();
    const s = useFillBlankSession(deckId);

    const { isBlocked, confirmLeave, cancelLeave } = useLeaveGuard(s.stage === "doing");

    const exit = () => {
        if (window.history.state?.idx > 0) navigate(-1);
        else navigate(ROUTE_PATHS.HUB);
    };

    return (
        <div className="mx-auto flex w-full max-w-3xl flex-col gap-4">
            <FillBlankSettingDialog
                open={s.stage === "setting"}
                isLoading={s.isCreating}
                errorMessage={s.createError ? "Could not create the test. Please try again." : undefined}
                onSubmit={s.create}
                onCancel={exit}
            />

            {s.stage === "result" && s.result && (
                <div className="flex flex-col items-center gap-4 rounded-xl bg-card p-8 ring-1 ring-foreground/10">
                    <h2 className="text-2xl font-semibold">Result</h2>
                    <p className="text-3xl font-bold text-[#818cf8]">
                        {s.result.correctCount ?? 0}/{s.result.totalCount ?? s.totalBlanks}
                    </p>
                    <div className="flex justify-center gap-2">
                        <Button variant="outline" onClick={s.restart}>Try again</Button>
                        <Button onClick={() => navigate(-1)}>Finish</Button>
                    </div>
                </div>
            )}

            {s.stage !== "setting" && (
                <>
                    {s.stage === "doing" && (
                        <div className="text-right text-xs text-muted-foreground">
                            {s.answeredCount}/{s.totalBlanks} answered
                        </div>
                    )}

                    {s.questions.map((q, i) => (
                        <FillBlankQuestionCard
                            key={i}
                            index={i}
                            question={q}
                            answers={s.answers}
                            results={s.resultByToken}
                            reviewing={s.stage === "result"}
                            onChange={s.setAnswer}
                        />
                    ))}

                    {s.stage === "doing" && (
                        <div className="flex flex-col items-center gap-2">
                            {s.submitError && (
                                <p className="text-sm text-red-400">Could not submit. Please try again.</p>
                            )}
                            <Button
                                onClick={s.submit}
                                disabled={!s.allAnswered || s.isSubmitting}
                                className="min-w-40 bg-[#312e81] text-white hover:bg-[#3f3b9e] border border-[#4c4699]"
                            >
                                {s.isSubmitting ? "Submitting..." : "Submit"}
                            </Button>
                        </div>
                    )}
                </>
            )}

            <LeaveConfirmDialog open={isBlocked} onStay={cancelLeave} onLeave={confirmLeave} />
        </div>
    );
}