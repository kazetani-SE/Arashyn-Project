import { useEffect } from "react";
import { Button } from "@/components/ui/button.tsx";
import { ArrowLeft } from "lucide-react";
import DiscoverSection from "@/features/discover/components/DiscoverSection.tsx";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select.tsx";
import { useLocation, useNavigate, useParams, useSearchParams } from "react-router-dom";
import type { AllType } from "@/features/popular/constants/all_type.ts";
import { CATEGORIES } from "@/features/popular/constants/categories.ts";
import { ROUTE_PATHS } from "@/app/router/route.ts";
import { SORT_OPTIONS } from "@/features/popular/constants/sort.ts";
import { FeatureBackground } from "@/components/background/FeatureBackground.tsx";
import { useSetBreadcrumb } from "@/layout/topbar/hooks/useSetBreadcrumb.ts";

export default function ItemListPage() {
    const { type: typeFromPath } = useParams<{ type: string }>();
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();
    const location = useLocation();

    const isViaPath = Boolean(typeFromPath);
    const rawType = typeFromPath ?? searchParams.get("type");
    const type = rawType as AllType | null;

    const isValid =
        !!type &&
        !!CATEGORIES[type] &&
        !(isViaPath && type === "search");

    const config = isValid && type ? CATEGORIES[type] : null;
    const sortOptions = isValid && type ? SORT_OPTIONS[type] : [];
    const query = searchParams.get("query");

    const breadcrumbKey = isViaPath ? `item_list:${type}` : `search:${type}`;

    const breadcrumbHref = isViaPath
        ? location.pathname
        : `${location.pathname}${location.search}`;

    useSetBreadcrumb(
        config ? config.title : "Discover",
        breadcrumbHref,
        breadcrumbKey
    );

    useEffect(() => {
        if (!isValid) {
            navigate(ROUTE_PATHS.DISCOVER, { replace: true });
        }
    }, [isValid, navigate]);

    if (!isValid || !type || !config) return null;

    const sortBy = searchParams.get("sort") ?? sortOptions[0]?.value ?? "";

    const handleSortChange = (value: string | null) => {
        const newSort = value ?? sortOptions[0]?.value ?? "";
        setSearchParams((prev) => {
            prev.set("sort", newSort);
            prev.set("page", "1");
            return prev;
        });
    };

    const description = query ? `Showing results for "${query}"` : config.description;
    const Icon = config.icon;

    const onBack = () => {
        navigate(-1);
    };

    return (
        <div className="container mx-auto max-w-7xl space-y-8 py-8 px-6">
            <FeatureBackground />

            <div className="space-y-4">
                <Button variant="ghost" className="gap-2" onClick={onBack}>
                    <ArrowLeft className="h-4 w-4" />
                    Back to Discover
                </Button>

                <div className="flex items-start justify-between gap-4">
                    <div className="space-y-2">
                        <div className="flex items-center gap-2">
                            <Icon className={config.iconClassName} />
                            <h1 className="text-4xl font-bold">{config.title}</h1>
                        </div>
                        <p className="text-muted-foreground">{description}</p>
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