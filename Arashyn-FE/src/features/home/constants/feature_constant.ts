import { Brain, RefreshCw, TrendingUp, Flame, Search, Users } from "lucide-react";
import type {FeatureItem} from "@/features/home/types/feature_types.ts";

export const FEATURES:FeatureItem[] = [
    {
        id: "grammar-mentor",
        title: "Grammar Through Structure",
        description: "Get instant, contexts-aware explanations for any grammar rule across 32 languages, adapted to your proficiency level.",
        icon: Brain,
    },
    {
        id: "spaced-repetition",
        title: "Spaced Repetition Engine",
        description: "Our review scheduler spaces practice at optimal intervals for long-term retention, not short-term memorization.",
        icon: RefreshCw,
    },
    {
        id: "progress-tracking",
        title: "Precision Progress Tracking",
        description: "Granular analytics show which patterns you've mastered, which need review, and your trajectory over time.",
        icon: TrendingUp,
    },
    {
        id: "gamification",
        title: "Streak & Gamification",
        description: "Daily streaks, XP points, achievement badges, and leaderboards make grammar practice genuinely motivating.",
        icon: Flame,
    },
    {
        id: "grammar-search",
        title: "Grammar Search",
        description: "Instantly search any concept across all languages you're learning. Rules, examples, and exercises in one click.",
        icon: Search,
    },
    {
        id: "community",
        title: "Learning Community",
        description: "Connect with learners studying the same language. Share mnemonics, ask questions, celebrate progress.",
        icon: Users,
    },
] as const;