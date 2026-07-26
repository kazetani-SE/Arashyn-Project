import { useState } from "react";
import { CATEGORIES } from "@/features/discover/constants/categories.ts";
import { Button } from "@/components/ui/button.tsx";
import { ArrowLeft } from "lucide-react";
import DiscoverSection from "@/features/discover/components/DiscoverSection.tsx";
import type { CategoryPartProps } from "@/features/discover/types/props.ts";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select.tsx";
import {SORT_OPTIONS} from "@/features/discover/constants/sort.ts";

export default function ItemListPart({ type, query, onBack }: CategoryPartProps) {
    const config = CATEGORIES[type];
    const sortOptions = SORT_OPTIONS[type];

    const [sortBy, setSortBy] = useState(sortOptions[0]?.value ?? "");

    const handleSortChange = (value: string | null) => {
        setSortBy(value ?? sortOptions[0]?.value ?? "");
    };

    const description = config.description || `Showing results for "${query}"`;

    if (!config) return null;

    const Icon = config.icon;

    return (
        <div className="container mx-auto max-w-7xl space-y-8 py-8 px-6">
            <div className="space-y-4">
                <Button variant="ghost" className="gap-2" onClick={onBack}>
                    <ArrowLeft className="h-4 w-4" />
                    Back to Discover
                </Button>

                <div className="flex items-start justify-between gap-4">
                    <div className="space-y-2">
                        <div className="flex items-center gap-2">
                            <Icon className={config.iconClassName} />
                            <h1 className="text-4xl font-bold">
                                {config.title}
                            </h1>
                        </div>

                        <p className="text-muted-foreground">
                            {description}
                        </p>

                    </div>

                    <Select value={sortBy} onValueChange={handleSortChange}>
                        <SelectTrigger className="w-[180px]">
                            <SelectValue placeholder="Sort by">
                                {(value: string) =>
                                    sortOptions.find((option) => option.value === value)?.label ?? "Sort by"
                                }
                            </SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            {sortOptions.map((option) => (
                                <SelectItem key={option.value} value={option.value}>
                                    {option.label}
                                </SelectItem>
                            ))}
                        </SelectContent>
                    </Select>
                </div>
            </div>

            <DiscoverSection
                title={config.title}
                icon={config.icon}
                iconClassName={config.iconClassName}
                itemCount={18}
                showViewAll={false}
            />
        </div>
    );
}