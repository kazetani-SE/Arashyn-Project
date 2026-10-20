import { FLASHCARD_RATINGS, type FlashcardRating } from "@/features/learning/constants/learning_types.ts";
import { Button } from "@/components/ui/button";

type Props = { onRate: (rating: FlashcardRating) => void };

export function FlashcardRatingBar({ onRate }: Props) {
    return (
        <div className="flex justify-center gap-3">
            {FLASHCARD_RATINGS.map((r) => (
                <Button
                    key={r.key}
                    variant="outline"
                    onClick={() => onRate(r.key)}
                    className={`min-w-32 ${r.className}`}
                >
                    {r.label}
                    <span className="ml-1 text-[10px] opacity-60">{r.shortcut}</span>
                </Button>
            ))}
        </div>
    );
}