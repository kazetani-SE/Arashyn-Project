import { SummarizeCard } from "@/components/item/SummarizeCard.tsx";

type HubFolderItem = {
    id?: string;
    name?: string;
};

type OnViewDetail = (id: string) => void;

export function HubFolderCards({
                                   items,
                                   onViewDetail,
                               }: {
    items: HubFolderItem[];
    onViewDetail: OnViewDetail;
}) {
    return (
        <>
            {items.map((folder) => {
                const id = folder.id ?? "";

                return (
                    <div
                        key={id}
                        className="cursor-pointer h-full"
                        onClick={() => onViewDetail(id)}
                    >
                        <SummarizeCard
                            className="h-full pointer-events-none"
                            title={""}
                            filters={[]}
                            patterns={[{ key: id, content: folder.name ?? "" }]}
                            meanings={[]}
                            onViewDetail={() => {}}
                        />
                    </div>
                );
            })}
        </>
    );
}