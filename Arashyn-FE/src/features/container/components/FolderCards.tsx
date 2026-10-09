import type {FolderSummariseResponse} from "@/lib/api/generated";
import {SummarizeCard} from "@/components/item/SummarizeCard.tsx";

type OnViewDetail = (id: string) => void;

export function FolderCards({
                                items,
                                onViewDetail,
                            }: {
    items: FolderSummariseResponse[];
    onViewDetail: OnViewDetail;
}) {
    return (
        <>
            {items.map((folder) => {
                const id = folder.id ?? "";

                return (
                    <SummarizeCard
                        key={id}
                        className="h-full"
                        title={""}
                        filters={[]}
                        patterns={[{ key: id, content: folder.name }]}
                        meanings={[]}
                        onViewDetail={() => onViewDetail(id)}
                    />
                );
            })}
        </>
    );
}