import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { FeatureBackground } from "@/components/background/FeatureBackground";
import { ROUTE_PATHS, ROUTES } from "@/app/router/route.ts";
import { useSetBreadcrumb } from "@/layout/topbar/hooks/useSetBreadcrumb";

import { BROWSABLE_TYPES } from "@/features/popular/constants/all_type.ts";
import {CATEGORIES} from "@/features/popular/constants/categories.ts";
import TrendingSection from "@/features/discover/components/TrendingSection.tsx";

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
            <div className="container mx-auto max-w-7xl space-y-12 py-8 px-6">
                <div className="space-y-2">
                    <h1 className="text-4xl font-bold">Discover</h1>
                    <p className="text-muted-foreground">
                        Explore popular grammar, decks, and folders shared by the community.
                    </p>
                </div>

                {BROWSABLE_TYPES.map((type) => {
                    const category = CATEGORIES[type];

                    return (
                        <TrendingSection
                            key={type}
                            type={type}
                            title={category.title}
                            icon={category.icon}
                            iconClassName={category.iconClassName}
                            onViewAll={() => handleSelectCategory(type)}
                        />
                    );
                })}
            </div>
        </div>
    );
}