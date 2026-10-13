import { UserSummarizeCard } from "@/components/item/UserSummarizeCard.tsx";
import { useGrammarSummaryList } from "@/shared/hook/grammar_component_build.ts";
import type { UserGrammarSummarisedResponse } from "@/lib/api/generated";

type OnViewDetail = (id: string) => void;

export function HubGrammarCards({
                                    items,
                                    onViewDetail,
                                }: {
    items: UserGrammarSummarisedResponse[];
    onViewDetail: OnViewDetail;
}) {
    const grammarItems = useGrammarSummaryList(
        items as Parameters<typeof useGrammarSummaryList>[0]
    );

    return (
        <>
            {grammarItems.map(({ id, title, patterns, meanings, filters }, index) => {
                const original = items[index];

                return (
                    <div
                        key={id}
                        className="cursor-pointer h-full"
                        onClick={() => onViewDetail(id)}
                    >
                        <UserSummarizeCard
                            className="h-full pointer-events-none"
                            title={title}
                            filters={filters}
                            patterns={patterns.map(({ groupKey, pattern }) => ({
                                key: groupKey,
                                content: pattern,
                            }))}
                            meanings={meanings}
                            proficiency={original?.proficiency}
                            lastReviewAt={original?.lastReviewAt}
                            onViewDetail={() => {}}
                        />
                    </div>
                );
            })}
        </>
    );
}