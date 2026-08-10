import {BookOpen, Flame, Folder, Search} from "lucide-react";
import type {CategoryProps} from "@/features/discover/types/props.ts";
import type {AllType} from "@/features/popular/constants/all_type.ts";

export const CATEGORIES = {
    search: {
        title: "Search Results",
        description: "",
        icon: Search,
        iconClassName: "h-5 w-5 text-emerald-500",
    },
    grammar: {
        title: "Popular Grammar",
        description: "Browse all popular grammar shared by the community.",
        icon: Flame,
        iconClassName: "h-5 w-5 text-orange-500",
    },
    deck: {
        title: "Popular Decks",
        description: "Browse all popular decks shared by the community.",
        icon: BookOpen,
        iconClassName: "h-5 w-5 text-sky-500",
    },
    folder: {
        title: "Popular Folders",
        description: "Browse all popular folders shared by the community.",
        icon: Folder,
        iconClassName: "h-5 w-5 text-yellow-500",
    },
} satisfies Record<AllType, CategoryProps>;