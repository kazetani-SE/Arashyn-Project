import {Flame, Globe, RefreshCw, TrendingUp} from "lucide-react";
import type {Feature} from "@/features/login/types/feature_type.ts";

export const FEATURES:Feature[] = [
    {
        icon: Globe,
        text: "Social ...",
    },
    {
        icon: RefreshCw,
        text: "Spaced repetition tuned to your learning curve",
    },
    {
        icon: TrendingUp,
        text: "Real-time mastery across every grammar concept",
    },
    {
        icon: Flame,
        text: "Streaks and gamification that actually motivate",
    },
];