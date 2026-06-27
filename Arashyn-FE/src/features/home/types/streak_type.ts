import type {LucideIcon} from "lucide-react";

export type Achievement = {
    icon: LucideIcon;
    label: string;
    color: string;
}

export type AchievementUser = {
    rank: number;
    name: string;
    xp: string;
    active?: boolean;
}