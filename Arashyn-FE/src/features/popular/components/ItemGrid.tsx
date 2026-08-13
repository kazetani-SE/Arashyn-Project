import { Card, CardContent, CardHeader } from "@/components/ui/card.tsx";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { useGrammarList } from "@/shared/hook/grammar_component_build.ts";
import { SummarizeCard } from "@/components/item/SummarizeCard.tsx";
import type { grammar_response } from "@/shared/responses/grammar_response.ts";

type ItemGridProps = {
    items: grammar_response[];
    isLoading?: boolean;
    isError?: boolean;
    /** Number of skeleton placeholders to show while loading. Defaults to the page size. */
    itemCount?: number;
};

const ITEMS_SIZE = 21;

export default function ItemGrid({
                                     items,
                                     isLoading = false,
                                     isError = false,
                                     itemCount = ITEMS_SIZE,
                                 }: ItemGridProps) {
    const grammarItems = useGrammarList(items);

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

    if (grammarItems.length === 0) {
        return (
            <div className="py-12 text-center text-muted-foreground">
                No results found.
            </div>
        );
    }

    return (
        <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
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
                />
            ))}
        </div>
    );
}