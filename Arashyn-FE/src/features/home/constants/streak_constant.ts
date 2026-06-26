import type {Achievement, AchievementUser} from "@/features/home/types/streak_type.ts";
import {Award, Flame, Star, Target, Users, Zap} from "lucide-react";

export const ACHIEVEMENTS:Achievement[] = [
    { icon: Flame, label: "47-Day Streak", color: "text-orange-400" },
    { icon: Star, label: "Grammar Master", color: "text-yellow-400" },
    { icon: Award, label: "Top 5% Learner", color: "text-indigo-400" },
    { icon: Target, label: "Perfect Week", color: "text-green-400" },
    { icon: Users, label: "Community Star", color: "text-pink-400" },
    { icon: Zap, label: "Speed Demon", color: "text-yellow-400" },
];

export const users:AchievementUser[] = [
    { rank: 1, name: "Emma S.", xp: "12,400" },
    { rank: 2, name: "Kenji M.", xp: "11,890" },
    { rank: 3, name: "You", xp: "11,200", active: true },
    { rank: 4, name: "Sofia L.", xp: "10,800" },
];