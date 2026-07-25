import {CATEGORIES} from "@/features/discover/constants/categories.ts";
import {Button} from "@/components/ui/button.tsx";
import {ArrowLeft} from "lucide-react";
import DiscoverSection from "@/features/discover/components/DiscoverSection.tsx";
import type {CategoryPartProps} from "@/features/discover/types/props.ts";

export default function ResultPart({ type, onBack }: CategoryPartProps) {
    const config = CATEGORIES[type];

    if (!config) return null;

    const Icon = config.icon;

    return (
        <div className="container mx-auto max-w-7xl space-y-8 py-8 px-6">
            <div className="space-y-4">
                <Button variant="ghost" className="gap-2" onClick={onBack}>
                    <ArrowLeft className="h-4 w-4" />
                    Back to Discover
                </Button>

                <div className="space-y-2">
                    <div className="flex items-center gap-2">
                        <Icon className={config.iconClassName} />
                        <h1 className="text-4xl font-bold">
                            {config.title}
                        </h1>
                    </div>

                    <p className="text-muted-foreground">
                        {config.description}
                    </p>
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