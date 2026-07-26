import type {LucideIcon} from "lucide-react";
import type {SeeAllType} from "@/features/discover/types/domains.ts";

export type SectionProps = {
    title: string;
    icon: LucideIcon;
    iconClassName?: string;
    itemCount?: number;
    showViewAll?: boolean;
    onViewAll?: () => void;
}

export type CategoryProps = {
    title: string;
    description: string;
    icon: LucideIcon;
    iconClassName: string;
}

export type CategoryPartProps = {
    type: SeeAllType;
    query?: string;
    onBack: () => void;
};