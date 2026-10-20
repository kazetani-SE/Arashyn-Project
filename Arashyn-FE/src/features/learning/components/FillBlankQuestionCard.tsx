import type { AnswerResult } from "@/lib/api/generated";
import type { QuestionView } from "@/features/learning/hook/use_fill_blank_session.ts";

type Props = {
    index: number;
    question: QuestionView;
    answers: Record<string, string>;
    results: Map<string, AnswerResult>;
    reviewing: boolean;
    onChange: (token: string, value: string) => void;
};

export function FillBlankQuestionCard({ index, question, answers, results, reviewing, onChange }: Props) {
    return (
        <div className="flex flex-col gap-3 rounded-xl bg-card p-5 text-card-foreground ring-1 ring-foreground/10">
            <div className="flex gap-3">
                <span className="flex size-6 shrink-0 items-center justify-center rounded-full bg-[#1e1b3a] text-xs font-medium text-[#a5adf0]">
                    {index + 1}
                </span>

                <div className="flex flex-wrap items-center gap-x-1.5 gap-y-2 text-lg">
                    {question.components.map((c, i) => {
                        const token = c.answerToken;

                        if (!token) return <span key={i}>{c.content}</span>;

                        const value = answers[token] ?? "";
                        const res = results.get(token);

                        if (reviewing) {
                            const ok = res?.correct === true;
                            return (
                                <span key={i} className="inline-flex flex-col items-center">
                                    <span
                                        className={`rounded-md border px-2 py-0.5 text-base font-medium ${
                                            ok
                                                ? "border-emerald-800 bg-emerald-900/40 text-emerald-200"
                                                : "border-red-800 bg-red-900/40 text-red-200"
                                        }`}
                                    >
                                        {value || "—"}
                                    </span>
                                    {!ok && res?.correctAnswer && (
                                        <span className="text-xs text-emerald-300">{res.correctAnswer}</span>
                                    )}
                                </span>
                            );
                        }

                        if (c.choices && c.choices.length > 0) {
                            return (
                                <select
                                    key={i}
                                    value={value}
                                    onChange={(e) => onChange(token, e.target.value)}
                                    className="rounded-md border border-[#1e1b3a] bg-[#0a0a12] px-2 py-1 text-base text-[#818cf8] outline-none focus:border-[#4c4699]"
                                >
                                    <option value="">...</option>
                                    {c.choices.map((choice) => (
                                        <option key={choice} value={choice}>
                                            {choice}
                                        </option>
                                    ))}
                                </select>
                            );
                        }

                        return (
                            <input
                                key={i}
                                value={value}
                                onChange={(e) => onChange(token, e.target.value)}
                                className="w-32 rounded-md border border-[#1e1b3a] bg-[#0a0a12] px-2 py-1 text-center text-base text-[#818cf8] outline-none focus:border-[#4c4699]"
                            />
                        );
                    })}
                </div>
            </div>

            {question.meaning && (
                <div className="border-t border-[#1e1b3a] pt-3 text-sm text-muted-foreground">
                    {question.meaning}
                </div>
            )}
        </div>
    );
}