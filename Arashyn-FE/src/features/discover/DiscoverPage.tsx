import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { FeatureBackground } from "@/components/background/FeatureBackground";
import { ROUTE_PATHS, ROUTES } from "@/app/router/route.ts";
import { useSetBreadcrumb } from "@/layout/topbar/hooks/useSetBreadcrumb";

import TrendingPart from "@/features/discover/parts/TrendingPart.tsx";
import { BROWSABLE_TYPES } from "@/features/popular/constants/all_type.ts";

export default function DiscoverPage() {
    useSetBreadcrumb("DiscoverPage", ROUTE_PATHS.DISCOVER);

    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const query = searchParams.get("query");

    useEffect(() => {
        if (query) {
            navigate(ROUTES.search({ query }), { replace: true });
        }
    }, [query, navigate]);

    const handleSelectCategory = (type: (typeof BROWSABLE_TYPES)[number]) => {
        navigate(ROUTES.itemList(type));
    };

    if (query) return null;

    return (
        <div>
            <FeatureBackground />
            <TrendingPart onSelectCategory={handleSelectCategory} />
        </div>
    );
}