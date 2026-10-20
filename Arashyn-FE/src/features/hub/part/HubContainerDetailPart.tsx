import { useNavigate } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { Button } from "@/components/ui/button.tsx";
import { ROUTE_PATHS, ROUTES } from "@/app/router/route.ts";
import ContainerSectionPart from "@/features/container/part/ContainerSectionPart.tsx";
import { HubDeckCards } from "@/features/hub/components/HubDeckCards.tsx";
import { HubFolderCards } from "@/features/hub/components/HubFolderCards.tsx";
import { HubGrammarCards } from "@/features/hub/components/HubGrammarCards.tsx";
import { useUserContainerDetail } from "@/features/hub/hook/use_user_container_detail.ts";
import type { UserDeckDetailResponse } from "@/lib/api/generated";
import type { UserFolderDetailResponse } from "@/lib/api/generated";
import type { Children } from "@/lib/api/generated";
import {createUserDeckActions, createUserFolderActions} from "@/features/hub/constants/user_actions.ts";
import {userDeckService} from "@/entities/hub/userdeck/user_deck_service.ts";
import {useState} from "react";
import {userFolderService} from "@/entities/hub/userfolder/user_folder_service.ts";
import {ConfirmDeleteDialog} from "@/features/hub/components/ConfirmDeleteDialog.tsx";
import {LearningTypeDialog} from "@/features/hub/components/LearningTypeDialog.tsx";
import type {LearningType} from "@/features/learning/constants/learning_types.ts";

type HubDetailPartProps = {
    type: "deck" | "folder";
    id: string;
};

export function HubContainerDetailPart({ type, id }: HubDetailPartProps) {
    const navigate = useNavigate();
    const { data, isLoading, isError } = useUserContainerDetail({ type, id });

    const [openDelete, setOpenDelete] = useState(false);
    const [isDeleting, setIsDeleting] = useState(false);
    const [openLearning, setOpenLearning] = useState(false);

    const goBack = () => {
        if (window.history.state?.idx > 0) {
            navigate(-1);
        } else {
            navigate(ROUTE_PATHS.HUB);
        }
    };

    const handleSelectLearningType = (learningType: LearningType) => {
        setOpenLearning(false);
        navigate(ROUTES.learning(id, learningType));
    };

    const goDeck = (itemId: string) => navigate(ROUTES.userDeckDetail(itemId));
    const goFolder = (itemId: string) => navigate(ROUTES.userFolderDetail(itemId));
    const goGrammar = (itemId: string) => navigate(ROUTES.userGrammarDetail(itemId));

    const deck = type === "deck" ? (data as UserDeckDetailResponse | undefined) : undefined;
    const folder = type === "folder" ? (data as UserFolderDetailResponse | undefined) : undefined;
    const detail = deck ?? folder;

    // ===== Folder children =====
    const children = Array.from(folder?.children ?? []) as Children[];
    const childFolders = children.filter((c) => c.isFolder === true);
    const childDecks = children.filter((c) => c.isFolder === false);

    // ===== Deck grammars =====
    const grammars = deck?.grammars?.items ?? [];

    if (isLoading) {
        return (
            <div className="space-y-4">
                <Skeleton className="h-6 w-24 rounded-full" />
                <Skeleton className="h-10 w-1/2" />
                <Skeleton className="h-4 w-2/3" />
            </div>
        );
    }

    // ===== Actions =====
    const deckActions = createUserDeckActions({
        id,
        onMove: (itemId) => {
            // TODO
            console.log("move deck", itemId);
        },
        onCopy: async (itemId) => {
            try {
                // TODO
                console.log("clone deck", itemId);
            } catch (e) {
                console.error(e);
            }
        },
        onDelete: () => setOpenDelete(true),
        onLearning: () => setOpenLearning(true),
    });

    const isDeck = type === "deck";

    const folderActions = createUserFolderActions({
        id,
        onMove: (itemId) => {
            // TODO
            console.log("move folder", itemId);
        },
        onCopy: async (itemId) => {
            try {
                // TODO
                console.log("clone deck", itemId);
            } catch (e) {
                console.error(e);
            }
        },
        onDelete: () => setOpenDelete(true),
    });

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            if (isDeck) userDeckService.delete(id)
            else userFolderService.delete(id);
            goBack();
        } finally {
            setIsDeleting(false);
        }
    };

    const actions = isDeck ? deckActions : folderActions;

    if (isError || !detail) {
        return (
            <p className="py-8 text-center text-muted-foreground">
                Something went wrong while loading. Please try again.
            </p>
        );
    }

    return (
        <>
            <div className="flex items-center justify-between mb-[1vh] mt-[1vh]">
                <Button
                    variant="ghost"
                    onClick={goBack}
                    className="-ml-3 mb-2 gap-1.5 text-muted-foreground hover:text-[#a5adf0]"
                >
                    <ArrowLeft className="h-4 w-4" />
                    Back
                </Button>

                {/* Action buttons */}
                <div className="flex items-center gap-2">
                    {actions.map((action) => (
                        <Button
                            key={action.key}
                            variant="outline"
                            size="sm"
                            onClick={action.onClick}
                        >
                            {action.label}
                        </Button>
                    ))}
                </div>
            </div>

            <div className="space-y-2">
                <h1 className="text-3xl font-bold tracking-tight">{detail.name}</h1>

                <div className="flex flex-wrap items-center gap-3 text-sm text-muted-foreground">
                    {deck?.language && (
                        <span className="rounded-full bg-muted px-2.5 py-0.5 text-xs font-medium">
                            {deck.language}
                          </span>
                    )}

                    {type === "deck" && deck?.deckId && (
                        <button
                            onClick={() => navigate(ROUTES.deckDetail(deck.deckId!))}
                            className="text-[#a5adf0] hover:underline"
                        >
                            View original deck
                        </button>
                    )}

                    {type === "folder" && folder?.systemFolderId && (
                        <button
                            onClick={() => navigate(ROUTES.folderDetail(folder.systemFolderId!))}
                            className="text-[#a5adf0] hover:underline"
                        >
                            View original folder
                        </button>
                    )}

                    {detail.createdAt && (
                        <span>Created {new Date(detail.createdAt).toLocaleDateString()}</span>
                    )}
                    {detail.updatedAt && (
                        <span>Updated {new Date(detail.updatedAt).toLocaleDateString()}</span>
                    )}
                </div>
            </div>

            {/* ===== DECK DETAIL ===== */}
            {type === "deck" && (
                <ContainerSectionPart title="Grammars" count={grammars.length}>
                    <HubGrammarCards items={grammars} onViewDetail={goGrammar} />
                </ContainerSectionPart>
            )}

            {/* ===== FOLDER DETAIL ===== */}
            {type === "folder" && (
                <>
                    <ContainerSectionPart title="Decks" count={childDecks.length}>
                        <HubDeckCards
                            items={childDecks.map((c) => ({
                                id: c.id,
                                name: c.name,
                            }))}
                            onViewDetail={goDeck}
                        />
                    </ContainerSectionPart>

                    <ContainerSectionPart title="Child folders" count={childFolders.length}>
                        <HubFolderCards
                            items={childFolders.map((c) => ({
                                id: c.id,
                                name: c.name,
                            }))}
                            onViewDetail={goFolder}
                        />
                    </ContainerSectionPart>
                </>
            )}

            <ConfirmDeleteDialog
                open={openDelete}
                onOpenChange={setOpenDelete}
                title="Delete this grammar?"
                description="This grammar will be permanently removed from your collection."
                isLoading={isDeleting}
                onConfirm={handleDelete}
            />

            {type === "deck" && (
                <LearningTypeDialog
                    open={openLearning}
                    onOpenChange={setOpenLearning}
                    onSelect={handleSelectLearningType}
                />
            )}
        </>
    );
}