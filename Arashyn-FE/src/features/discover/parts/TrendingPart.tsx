import DiscoverSection from "@/features/discover/components/DiscoverSection.tsx";
import {CATEGORIES} from "@/features/discover/constants/categories.ts";
import type {SeeAllType} from "@/features/discover/types/domains.ts";

type TrendingPartProps = {
    onSelectCategory: (type: SeeAllType) => void;
};

function TrendingPart({onSelectCategory}: TrendingPartProps) {
    return (
        <div className="container mx-auto max-w-7xl space-y-12 py-8 px-6">
            <div className="space-y-2">
                <h1 className="text-4xl font-bold">Discover</h1>
                <p className="text-muted-foreground">
                    Explore popular grammar, decks, and folders shared by the community.
                </p>
            </div>

            {(Object.entries(CATEGORIES) as [SeeAllType, typeof CATEGORIES[SeeAllType]][])
                .filter(([type]) => type !== "search")
                .map(([type, category]) => (
                    <DiscoverSection
                        key={type}
                        title={category.title}
                        icon={category.icon}
                        iconClassName={category.iconClassName}
                        onViewAll={() => onSelectCategory(type)}
                    />
                ))}
        </div>
    );
}

export default TrendingPart