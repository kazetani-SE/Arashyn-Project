import type {DeckSummariseResponse} from "@/lib/api/generated";
import {SummarizeCard} from "@/components/item/SummarizeCard.tsx";

type OnViewDetail = (id: string) => void;

export function DeckCards({
                              items,
                              onViewDetail,
                          }: {
    items: DeckSummariseResponse[];
    onViewDetail: OnViewDetail;
}) {
    return (
        <>
            {items.map((deck) => {
                const id = deck.id ?? "";

                return (
                    <SummarizeCard
                        key={id}
                        className="h-full"
                        title={""}
                        filters={deck.language ? [deck.language] : []}
                        patterns={[{ key: id, content: deck.name }]}
                        meanings={deck.description ? [deck.description] : []}
                        onViewDetail={() => onViewDetail(id)}
                    />
                );
            })}
        </>
    );
}