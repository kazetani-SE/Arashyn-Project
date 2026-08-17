import { Button } from "@/components/ui/button.tsx";
import { Card, CardContent, CardHeader } from "@/components/ui/card.tsx";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { useGrammarList } from "@/shared/hook/grammar_component_build.ts";
import { SummarizeCard } from "@/components/item/SummarizeCard.tsx";
import type { LucideIcon } from "lucide-react";
import {useItemList} from "@/features/popular/hook/use_item_list.ts";
import type {BrowsableType} from "@/features/popular/constants/all_type.ts";

type DiscoverSectionProps = {
    type: BrowsableType;
    title: string;
    icon: LucideIcon;
    iconClassName?: string;
    onViewAll: () => void;
};

const TRENDING_SIZE = 6;

export default function TrendingSection({
                                            type,
                                            title,
                                            icon: Icon,
                                            iconClassName,
                                            onViewAll,
                                        }: DiscoverSectionProps) {
    // Real call (through MSW mock for now). Only "grammar" is wired up on
    // the backend/mock side — for "deck"/"folder", useItemList's `enabled`
    // guard keeps this from firing, so it'll just render "No results found".
    const { data, isLoading, isError } = useItemList({
        type,
        page: 0,
        size: TRENDING_SIZE,
    });

    // Maps raw grammar_response[] -> display shape ({ id, title, patterns, meanings, filters }).
    const items = useGrammarList(data?.items ?? []);

    return (
        <section className="space-y-4">
            <div className="flex items-center justify-between">
                <div className="flex items-center gap-2">
                    <Icon className={iconClassName} />
                    <h2 className="text-xl font-semibold">{title}</h2>
                </div>

                <Button variant="ghost" onClick={onViewAll}>
                    View all
                </Button>
            </div>

            <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
                {isLoading &&
                    Array.from({ length: TRENDING_SIZE }).map((_, index) => (
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

                {!isLoading && isError && (
                    <p className="col-span-full py-8 text-center text-muted-foreground">
                        Something went wrong while loading. Please try again.
                    </p>
                )}

                {!isLoading && !isError && items.length === 0 && (
                    <p className="col-span-full py-8 text-center text-muted-foreground">
                        No results found.
                    </p>
                )}

                {!isLoading &&
                    !isError &&
                    items.map(({ id, title: itemTitle, patterns, meanings, filters }) => (
                        <SummarizeCard
                            key={id}
                            className="h-full"
                            title={itemTitle}
                            filters={filters}
                            patterns={patterns.map(({ groupKey, pattern }) => ({
                                key: groupKey,
                                content: pattern,
                            }))}
                            meanings={meanings}
                        />
                    ))}
            </div>
        </section>
    );
}