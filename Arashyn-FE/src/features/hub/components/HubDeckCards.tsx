import { SummarizeCard } from "@/components/item/SummarizeCard.tsx";

type HubDeckItem = {
    id?: string;
    name?: string;
    description?: string;
    language?: string;
    lastOpenAt?: string;
};

type OnViewDetail = (id: string) => void;

export function HubDeckCards({
                                 items,
                                 onViewDetail,
                             }: {
    items: HubDeckItem[];
    onViewDetail: OnViewDetail;
}) {
    return (
        <>
            {items.map((deck) => {
                const id = deck.id ?? "";

                return (
                    <div
                        key={id}
                        className="cursor-pointer h-full"
                        onClick={() => onViewDetail(id)}
                    >
                        <SummarizeCard
                            className="h-full pointer-events-none"
                            title={""}
                            filters={deck.language ? [deck.language] : []}
                            patterns={[{ key: id, content: deck.name ?? "" }]}
                            meanings={deck.description ? [deck.description] : []}
                            onViewDetail={() => {}}
                        />
                    </div>
                );
            })}
        </>
    );
}