import type {HeroBenefit, HeroParticle} from "@/features/home/types/hero_types.ts";

export const HERO_BENEFITS: HeroBenefit[] = [
    {
        id: "trial",
        label: "Free trial unlimited",
    },
    {
        id: "card",
        label: "No credit card required",
    },
    {
        id: "language",
        label: "32 languages supported",
    },
];

export const HERO_PARTICLES: HeroParticle[] = [
    {
        id: "1",
        value: "S + V + O",
    },
    {
        id: "2",
        value: "→ clause",
    },
    {
        id: "3",
        value: "Present Perfect",
    },
    {
        id: "4",
        value: "て形",
    },
    {
        id: "5",
        value: "subjunctive",
    },
];

export const HERO_COLORS = {
    glow: "bg-indigo-500/20",
    glowSecondary: "bg-violet-500/20",
    button: "bg-indigo-500",
};