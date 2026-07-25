import {ArrowRight, Check } from "lucide-react";
import {HERO_BENEFITS} from "@/features/home/constants/hero_constant.ts";
import {useNavigate} from "react-router-dom";
import {ROUTE_PATHS} from "@/app/router/route.ts";

export default function HeroSection() {
    return (
        <section className="relative z-10 w-full mt-[10vh]">

            <div className="container mx-auto px-6">
                <div className="flex min-h-[75vh] flex-col items-center justify-center">

                    <HeroHeading />

                    <HeroDescription />

                    <HeroActions />

                    <HeroBenefits />
                </div>
            </div>
        </section>
    );
}

function HeroHeading() {
    return (
        <div className="max-w-[80vw] text-center">
            <h1 className="text-5xl font-bold leading-tight md:text-7xl xl:text-8xl">
                Master Any Grammar.
            </h1>
            <h1 className="from-primary to-violet-500 bg-gradient-to-r bg-clip-text text-5xl font-bold
            text-transparent md:text-7xl xl:text-8xl">
                Think in Any Language.
            </h1>
        </div>
    );
}

function HeroDescription() {
    return (
        <p className="text-muted-foreground mt-8 max-w-2xl text-center text-lg">
            Arashyn uses Component-based method to learn grammar the way linguists think about it —
            structurally, intelligently, and with context that actually sticks.
        </p>
    );
}

function HeroActions() {
    const navigate = useNavigate();

    const startHandler = () => {
        navigate(ROUTE_PATHS.DISCOVER);
    }

    return (
        <div className="mt-10 flex items-center gap-4">
            <button className="bg-indigo-500 text-primary-foreground !text-white font-bold
            inline-flex items-center gap-2 rounded-xl px-8 py-4 hover:bg-indigo-600 cursor-pointer"
            onClick={startHandler}
            >
                Start for Free
                <ArrowRight className="size-4" />
            </button>
        </div>
    );
}

function HeroBenefits() {
    return (
        <div className="mt-8 flex flex-wrap justify-center gap-6">
            {HERO_BENEFITS.map((item) => (
                <div key={item.id} className="flex items-center gap-2">
                    <Check className="text-primary size-4" />
                    <span className="text-muted-foreground text-sm">{item.label}</span>
                </div>
            ))}
        </div>
    );
}