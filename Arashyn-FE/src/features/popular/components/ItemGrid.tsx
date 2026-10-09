import { Card, CardContent, CardHeader } from "@/components/ui/card.tsx";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { SummarizeCard } from "@/components/item/SummarizeCard.tsx";
import type { DeckSummariseResponse, FolderListResponse } from "@/lib/api/generated";
import {useGrammarSummaryList} from "@/shared/hook/grammar_component_build.ts";
import {ROUTES} from "@/app/router/route.ts";
import {useNavigate} from "react-router-dom";
import type {BrowsableType} from "@/features/popular/constants/all_type.ts";

type FolderItem = NonNullable<FolderListResponse["items"]>[number];

type ItemGridProps = {
    type: BrowsableType;
    items: unknown[];
    isLoading?: boolean;
    isError?: boolean;
    /** Number of skeleton placeholders to show while loading. Defaults to the page size. */
    itemCount?: number;
};

const ITEMS_SIZE = 21;

const EMPTY: never[] = [];

export default function ItemGrid({
                                     type,
                                     items,
                                     isLoading = false,
                                     isError = false,
                                     itemCount = ITEMS_SIZE,
                                 }: ItemGridProps) {
    const navigate = useNavigate();

    const grammarItems = useGrammarSummaryList(
        type === "grammar"
            ? (items as Parameters<typeof useGrammarSummaryList>[0])
            : EMPTY
    );
    const deckItems = type === "deck" ? (items as DeckSummariseResponse[]) : EMPTY;
    const folderItems = type === "folder" ? (items as FolderItem[]) : EMPTY;

    const count =
        type === "grammar"
            ? grammarItems.length
            : type === "deck"
                ? deckItems.length
                : folderItems.length;

    if (isLoading) {
        return (
            <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
                {Array.from({ length: itemCount }).map((_, index) => (
                    <Card key={index}>
                        <CardHeader>
                            <Skeleton className="h-5 w-2/3" />
                        </CardHeader>
                        <CardContent className="space-y-4">
                            <Skeleton className="h-4 w-full" />
                            <Skeleton className="h-4 w-5/6" />
                            <div className="flex gap-2 pt-2">
                                <Skeleton className="h-6 w-16 rounded-full" />
                                <Skeleton className="h-6 w-20 rounded-full" />
                            </div>
                            <div className="flex justify-between pt-4">
                                <Skeleton className="h-4 w-16" />
                                <Skeleton className="h-4 w-20" />
                            </div>
                        </CardContent>
                    </Card>
                ))}
            </div>
        );
    }

    if (isError) {
        return (
            <div className="py-12 text-center text-muted-foreground">
                Something went wrong while loading. Please try again.
            </div>
        );
    }

    if (count === 0) {
        return (
            <div className="py-12 text-center text-muted-foreground">
                No results found.
            </div>
        );
    }

    return (
        <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
            {type === "grammar" &&
                grammarItems.map(({ id, title, patterns, meanings, filters }) => (
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
                        onViewDetail={() => navigate(ROUTES.grammarDetail(id))}
                    />
                ))}

            {type === "deck" &&
                deckItems.map((deck) => {
                    const id = deck.id ?? "";

                    return (
                        <SummarizeCard
                            key={id}
                            className="h-full"
                            title={""}
                            filters={deck.language ? [deck.language] : []}
                            patterns={[{ key: id, content: deck.name }]}
                            meanings={deck.description ? [deck.description] : []}
                            onViewDetail={() => navigate(ROUTES.deckDetail(id))}
                        />
                    );
                })}

            {type === "folder" &&
                folderItems.map((folder) => {
                    const id = folder.id ?? "";

                    return (
                        <SummarizeCard
                            key={id}
                            className="h-full"
                            title={""}
                            filters={[]}
                            patterns={[{ key: id, content: folder.name }]}
                            meanings={[]}
                            onViewDetail={() => navigate(ROUTES.folderDetail(id))}
                        />
                    );
                })}
        </div>
    );
}