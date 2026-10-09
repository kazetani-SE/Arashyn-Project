import { useNavigate, useParams } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import { FeatureBackground } from "@/components/background/FeatureBackground";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { Button } from "@/components/ui/button.tsx";
import { ROUTE_PATHS, ROUTES } from "@/app/router/route.ts";
import type { DeckDetailResponse, FolderDetailResponse } from "@/lib/api/generated";
import ContainerSectionPart from "./part/ContainerSectionPart";
import {FolderCards} from "@/features/container/components/FolderCards.tsx";
import {DeckCards} from "@/features/container/components/DeckCards.tsx";
import {GrammarCards} from "@/features/container/components/GrammarCards.tsx";
import ContainerHeaderPart from "@/features/container/part/ContainerHeaderPart.tsx";
import {useContainerDetail} from "@/features/container/hook/use_container_detail.ts";
import {useSetBreadcrumb} from "@/layout/topbar/hooks/useSetBreadcrumb.ts";
import {useBreadcrumbStore} from "@/shared/store/breadcrumb_store.ts";

type ContainerDetailPageProps = {
    type: "deck" | "folder";
};

export default function ContainerDetailPage({ type }: ContainerDetailPageProps) {
    const navigate = useNavigate();
    const { id = "" } = useParams<{ id: string }>();

    const { data, isLoading, isError } = useContainerDetail({ type, id });

    const goBack = () => {
        if (window.history.state?.idx > 0) {
            navigate(-1);
        } else {
            navigate(ROUTE_PATHS.DISCOVER);
        }
    };

    const goGrammar = (itemId: string) => navigate(ROUTES.grammarDetail(itemId));

    const goDeck = (itemId: string) => {
        navigate(ROUTES.deckDetail(itemId));
    };

    const goFolder = (itemId: string) => {
        navigate(ROUTES.folderDetail(itemId));
    };

    const deck = type === "deck" ? (data as DeckDetailResponse | undefined) : undefined;
    const folder = type === "folder" ? (data as FolderDetailResponse | undefined) : undefined;
    const detail = deck ?? folder;

    useSetBreadcrumb(
        detail?.name,
        type === "deck" ? ROUTES.deckDetail(id) : ROUTES.folderDetail(id),
        id
    );
    useBreadcrumbStore((s) => s.pushOrJump);

    const deckFolders = Array.from(deck?.folders ?? []);
    const deckGrammars = Array.from(deck?.grammars ?? []);
    const folderDecks = Array.from(folder?.decks ?? []);
    const folderChildren = Array.from(folder?.childFolders ?? []);

    return (
        <div>
            <FeatureBackground />

            <div className="container mx-auto max-w-7xl space-y-12 py-2 px-6">
                <Button
                    variant="ghost"
                    onClick={goBack}
                    className="-ml-3 mb-2 gap-1.5 text-muted-foreground hover:text-[#a5adf0]"
                >
                    <ArrowLeft className="h-4 w-4" />
                    Back
                </Button>

                {isLoading && (
                    <div className="space-y-4">
                        <Skeleton className="h-6 w-24 rounded-full" />
                        <Skeleton className="h-10 w-1/2" />
                        <Skeleton className="h-4 w-2/3" />
                    </div>
                )}

                {!isLoading && (isError || !detail) && (
                    <p className="py-8 text-center text-muted-foreground">
                        Something went wrong while loading. Please try again.
                    </p>
                )}

                {!isLoading && !isError && detail && (
                    <>
                        <ContainerHeaderPart
                            type={type}
                            name={detail.name}
                            ownerId={detail.ownerId}
                            ownerName={detail.ownerName}
                            createdAt={detail.createdAt}
                            updatedAt={detail.updatedAt}
                            language={deck?.language}
                        />

                        {type === "deck" && (
                            <>
                                <ContainerSectionPart title="Grammars" count={deckGrammars.length}>
                                    <GrammarCards items={deckGrammars} onViewDetail={goGrammar} />
                                </ContainerSectionPart>

                                <ContainerSectionPart title="Folders" count={deckFolders.length}>
                                    <FolderCards items={deckFolders} onViewDetail={goFolder} />
                                </ContainerSectionPart>
                            </>
                        )}

                        {type === "folder" && (
                            <>
                                <ContainerSectionPart title="Decks" count={folderDecks.length}>
                                    <DeckCards items={folderDecks} onViewDetail={goDeck} />
                                </ContainerSectionPart>

                                <ContainerSectionPart title="Child folders" count={folderChildren.length}>
                                    <FolderCards items={folderChildren} onViewDetail={goFolder} />
                                </ContainerSectionPart>
                            </>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}