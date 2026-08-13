import TrendingSection from "@/features/discover/components/TrendingSection.tsx";
import { CATEGORIES } from "@/features/popular/constants/categories.ts";
import { BROWSABLE_TYPES } from "@/features/popular/constants/all_type.ts";

type TrendingPartProps = {
    onSelectCategory: (type: (typeof BROWSABLE_TYPES)[number]) => void;
};

function TrendingPart({ onSelectCategory }: TrendingPartProps) {
    return (
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
                        onViewAll={() => onSelectCategory(type)}
                    />
                );
            })}
        </div>
    );
}

export default TrendingPart;