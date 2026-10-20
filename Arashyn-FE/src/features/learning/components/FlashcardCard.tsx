import type { LearningGrammar } from "@/features/learning/types/learning_types.ts";

type Props = { grammar: LearningGrammar; flipped: boolean; onFlip: () => void };

export function FlashcardCard({ grammar, flipped, onFlip }: Props) {
    const { patterns, meanings } = grammar;

    return (
        <div
            onClick={onFlip}
            className="flex min-h-72 cursor-pointer select-none flex-col items-center justify-center gap-6 rounded-xl
                bg-card p-8 text-card-foreground ring-1 ring-foreground/10 transition-all duration-200
                hover:bg-[#12101f] hover:ring-[#1e1b3a] hover:shadow-[0_10px_40px_-10px_rgba(49,46,129,0.55)]"
        >
            <div className="flex flex-col items-center gap-3">
                {patterns.map(({ groupKey, pattern }, i) => (
                    <div key={groupKey} className="flex items-center gap-2">
                        {patterns.length > 1 && (
                            <span className="flex size-5 items-center justify-center rounded-full bg-[#1e1b3a] text-xs font-medium text-[#a5adf0]">
                                {i + 1}
                            </span>
                        )}
                        <span className="text-center text-2xl font-medium tracking-tight text-[#818cf8]">
                            {pattern}
                        </span>
                    </div>
                ))}
            </div>

            {flipped ? (
                <div className="flex w-full flex-col gap-2 border-t border-[#1e1b3a] pt-4">
                    {meanings.map((m, i) => (
                        <div key={i} className="flex gap-2 text-sm leading-relaxed text-neutral-300">
                            <span className="shrink-0 font-medium">{i + 1}.</span>
                            <span>{m}</span>
                        </div>
                    ))}
                </div>
            ) : (
                <div className="text-xs text-muted-foreground/70">Click or press Space to reveal meaning</div>
            )}
        </div>
    );
}