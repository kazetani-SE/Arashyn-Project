import { useState } from "react";
import { FeatureBackground } from "@/components/background/FeatureBackground";
import { ROUTE_PATHS } from "@/app/router/route";
import { useSetBreadcrumb } from "@/layout/topbar/hooks/useSetBreadcrumb";

import TrendingPart from "@/features/discover/parts/TrendingPart.tsx";
import ItemListPart from "@/features/discover/parts/ItemListPart.tsx";
import type { SeeAllType } from "@/features/discover/types/domains.ts";
import {useSearchParams} from "react-router-dom";

export default function DiscoverPage() {
    useSetBreadcrumb("DiscoverPage", ROUTE_PATHS.DISCOVER);

    const [searchParams, setSearchParams] = useSearchParams();
    const query = searchParams.get("q");

    const [selectedType, setSelectedType] = useState<SeeAllType | null>(null);

    const effectiveType: SeeAllType | null = query ? "search" : selectedType;

    const handleBack = () => {
        setSelectedType(null);
        if (query) {
            setSearchParams({});
        }
    };

    return (
        <div>
            <FeatureBackground />

            {effectiveType === null ? (
                <TrendingPart onSelectCategory={setSelectedType} />
            ) : (
                <ItemListPart
                    type={effectiveType}
                    query={effectiveType === "search" ? query ?? undefined : undefined}
                    onBack={handleBack}
                />
            )}
        </div>
    );
}