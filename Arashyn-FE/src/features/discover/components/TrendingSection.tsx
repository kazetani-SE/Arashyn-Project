import { Button } from "@/components/ui/button.tsx";
import { Card, CardContent, CardHeader } from "@/components/ui/card.tsx";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import {useGrammarSummaryList} from "@/shared/hook/grammar_component_build.ts";
import { SummarizeCard } from "@/components/item/SummarizeCard.tsx";
import type { LucideIcon } from "lucide-react";
import {useItemList} from "@/features/popular/hook/use_item_list.ts";
import type {BrowsableType} from "@/features/popular/constants/all_type.ts";
import {useNavigate} from "react-router-dom";
import {ROUTES} from "@/app/router/route.ts";
import {useLanguageStore} from "@/shared/store/language_store.ts";
import type { DeckSummariseResponse, FolderListResponse } from "@/lib/api/generated";

type FolderItem = NonNullable<FolderListResponse["items"]>[number];

type DiscoverSectionProps = {
    type: BrowsableType;
    title: string;
    icon: LucideIcon;
    iconClassName?: string;
    onViewAll: () => void;
};

const TRENDING_SIZE = 6;

const EMPTY: never[] = [];

export default function TrendingSection({
                                            type,
                                            title,
                                            icon: Icon,
                                            iconClassName,
                                            onViewAll,
                                        }: DiscoverSectionProps) {
    const navigate = useNavigate();

    const grammarDetail = (itemId: string) => {
        navigate(ROUTES.grammarDetail(itemId));
    };

    const deckDetail = (itemId: string) => {
        navigate(ROUTES.deckDetail(itemId));
    };

    const folderDetail = (itemId: string) => {
        navigate(ROUTES.folderDetail(itemId));
    };

    const language = useLanguageStore((s) => s.language) || undefined;

    const { data, isLoading, isError } = useItemList({
        type,
        page: 0,
        size: TRENDING_SIZE,
        language,
    });

    const rawItems = data?.items ?? EMPTY;

    const grammarItems = useGrammarSummaryList(
        type === "grammar"
            ? (rawItems as Parameters<typeof useGrammarSummaryList>[0])
            : EMPTY
    );

    const deckItems =
        type === "deck" ? (rawItems as DeckSummariseResponse[]) : EMPTY;

    const folderItems =
        type === "folder" ? (rawItems as FolderItem[]) : EMPTY;

    const itemCount =
        type === "grammar"
            ? grammarItems.length
            : type === "deck"
                ? deckItems.length
                : folderItems.length;

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

                {!isLoading && !isError && itemCount === 0 && (
                    <p className="col-span-full py-8 text-center text-muted-foreground">
                        No results found.
                    </p>
                )}

                {/* Grammar */}
                {!isLoading &&
                    !isError &&
                    type === "grammar" &&
                    grammarItems.map(({ id, title: itemTitle, patterns, meanings, filters }) => (
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
                            onViewDetail={() => grammarDetail(id)}
                        />
                    ))}

                {!isLoading &&
                    !isError &&
                    type === "deck" &&
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
                                onViewDetail={() => deckDetail(id)}
                            />
                        );
                    })}

                {!isLoading &&
                    !isError &&
                    type === "folder" &&
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
                                onViewDetail={() => folderDetail(id)}
                            />
                        );
                    })}
            </div>
        </section>
    );
}