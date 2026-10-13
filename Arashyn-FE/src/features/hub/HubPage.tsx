import { useParams, useLocation } from "react-router-dom";
import { FeatureBackground } from "@/components/background/FeatureBackground.tsx";
import { ROUTE_PATHS, ROUTES } from "@/app/router/route.ts";
import { useSetBreadcrumb } from "@/layout/topbar/hooks/useSetBreadcrumb.ts";
import { HubRootPart } from "@/features/hub/part/HubRootPart.tsx";
import { HubDetailPart } from "@/features/hub/part/HubDetailPart.tsx";
import { HubGrammarDetailPart } from "@/features/hub/part/HubGrammarDetailPart.tsx";

export default function HubPage() {
    const { id } = useParams<{ id: string }>();
    const location = useLocation();

    const isDeckDetail = location.pathname.startsWith("/hub/deck/");
    const isFolderDetail = location.pathname.startsWith("/hub/folder/");
    const isGrammarDetail = location.pathname.startsWith("/hub/grammar/");
    const isDetail = isDeckDetail || isFolderDetail || isGrammarDetail;

    const type = isDeckDetail
        ? "deck"
        : isFolderDetail
            ? "folder"
            : isGrammarDetail
                ? "grammar"
                : null;

    useSetBreadcrumb(
        isDetail ? undefined : "Hub",
        isDetail
            ? type === "deck"
                ? ROUTES.userDeckDetail(id ?? "")
                : type === "folder"
                    ? ROUTES.userFolderDetail(id ?? "")
                    : ROUTES.userGrammarDetail(id ?? "")
            : ROUTE_PATHS.HUB,
        id,
    );

    return (
        <div>
            <FeatureBackground />
            <div className="container mx-auto max-w-7xl space-y-12 py-2 px-6">
                {isGrammarDetail && id ? (
                    <HubGrammarDetailPart id={id} />
                ) : isDetail && type && id ? (
                    <HubDetailPart type={type as "deck" | "folder"} id={id} />
                ) : (
                    <HubRootPart />
                )}
            </div>
        </div>
    );
}