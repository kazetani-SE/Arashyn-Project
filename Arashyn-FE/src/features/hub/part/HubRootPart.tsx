import { useNavigate } from "react-router-dom";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import ContainerSectionPart from "@/features/container/part/ContainerSectionPart.tsx";
import { HubDeckCards } from "@/features/hub/components/HubDeckCards.tsx";
import { HubFolderCards } from "@/features/hub/components/HubFolderCards.tsx";
import { useHubRoot } from "@/features/hub/hook/use_hub_root.ts";
import { ROUTES } from "@/app/router/route.ts";

export function HubRootPart() {
    const navigate = useNavigate();
    const { folders, decks, isLoading, isError } = useHubRoot();

    const goDeck = (id: string) => navigate(ROUTES.userDeckDetail(id));
    const goFolder = (id: string) => navigate(ROUTES.userFolderDetail(id));

    if (isLoading) {
        return (
            <div className="space-y-4">
                <Skeleton className="h-6 w-24 rounded-full" />
                <Skeleton className="h-10 w-1/2" />
                <Skeleton className="h-4 w-2/3" />
            </div>
        );
    }

    if (isError) {
        return (
            <p className="py-8 text-center text-muted-foreground">
                Something went wrong while loading. Please try again.
            </p>
        );
    }

    return (
        <>
            <ContainerSectionPart title="My Decks" count={decks.length}>
                <HubDeckCards items={decks} onViewDetail={goDeck} />
            </ContainerSectionPart>

            <ContainerSectionPart title="My Folders" count={folders.length}>
                <HubFolderCards items={folders} onViewDetail={goFolder} />
            </ContainerSectionPart>
        </>
    );
}