import type {BreadcrumbProps} from "@/layout/topbar/types/topbar_types.ts";

export type BreadcrumbItem = BreadcrumbProps["items"][number];

export type BreadcrumbContextType = {
    items: BreadcrumbItem[];
    pushOrJump: (item: BreadcrumbItem) => void;
    reset: (items?: BreadcrumbItem[]) => void;
};