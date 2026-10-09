import { SummarizeCard } from "@/components/item/SummarizeCard.tsx";
import { useGrammarSummaryList } from "@/shared/hook/grammar_component_build.ts";
import type { GrammarSummaryResponse } from "@/lib/api/generated";

type OnViewDetail = (id: string) => void;

export function GrammarCards({
                                 items,
                                 onViewDetail,
                             }: {
    items: GrammarSummaryResponse[];
    onViewDetail: OnViewDetail;
}) {
    const grammarItems = useGrammarSummaryList(
        items as Parameters<typeof useGrammarSummaryList>[0]
    );

    return (
        <>
            {grammarItems.map(({ id, title, patterns, meanings, filters }) => (
                <SummarizeCard
                    key={id}
                    className="h-full"
                    title={title}
                    filters={filters}
                    patterns={patterns.map(({ groupKey, pattern }) => ({
                        key: groupKey,
                        content: pattern,
                    }))}
                    meanings={meanings}
                    onViewDetail={() => onViewDetail(id)}
                />
            ))}
        </>
    );
}