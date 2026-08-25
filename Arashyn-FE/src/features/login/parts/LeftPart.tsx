import {BookOpen, Sparkles, Star} from "lucide-react";
import {FEATURES} from "@/features/login/constants/feature_constant.ts";
import {STATS} from "@/features/login/constants/stat_constant.ts";
import {useNavigate} from "react-router-dom";
import {ROUTE_PATHS} from "@/app/router/route.ts";

export default function LeftPart(){
    const nav = useNavigate();

    return (
        <div className="flex flex-col gap-1 p-[3.5vw]">
            <button
                className="flex items-center gap-3 cursor-pointer !w-fit"
                onClick={() => nav(ROUTE_PATHS.DEFAULT)}
            >
                <div className="border-white/10 bg-indigo-500/10 flex h-8 w-8 items-center justify-center rounded-lg border">
                    <BookOpen className="size-4 text-indigo-400" />
                </div>
                <span className="text-2xl font-bold tracking-tight">Arashyn</span>
            </button>

            <LoginHero/>
        </div>
    );
}

function LoginHero() {
    return (
        <div className="relative z-10 py-12">
            <div className="border-indigo-500/20 bg-indigo-500/10 text-indigo-400 inline-flex items-center
            rounded-full border px-4 py-2 text-xs font-semibold uppercase tracking-[0.2em]">
                <Sparkles className="size-4 text-indigo-400 mr-2"/>
                    Trusted by 12,000+ learners
            </div>

            <h2 className="mt-5 mb-5 text-5xl font-extrabold leading-tight">
                The grammar platform your teachers never had.
            </h2>

            <p className="mb-10 text-xl text-white/60">
                Powerful learning methods, ... social community, spaced repetition, and progress
                tracking — all in one premium interface.
            </p>

            <LoginFeatureList />

            <LoginTestimonial />

            <LoginStats/>
        </div>
    );
}

function LoginFeatureList() {
    return (
        <div className="mb-10 flex flex-col gap-4 mt-[-1vh]">
            {FEATURES.map(({ icon: Icon, text }) => (
                <div
                    key={text}
                    className="flex items-center gap-3"
                >
                    <div className="flex h-8 w-8 shrink-0 items-center justify-center rounded-lg bg-indigo-500/10">
                        <Icon className="size-4 text-indigo-400" />
                    </div>

                    <span className="text-base text-white/60">
                        {text}
                    </span>
                </div>
            ))}
        </div>
    );
}

function LoginTestimonial() {
    return (
        <div className="rounded-3xl border border-white/10 bg-slate-900/20 p-5">
            <div className="mb-3 flex items-center gap-1">
                {Array.from({ length: 5 }).map((_, index) => (
                    <Star
                        key={index}
                        className="size-3 fill-yellow-400 text-yellow-400"
                    />
                ))}
            </div>

            <p className="mb-3 text-sm italic leading-relaxed text-white">
                "This is what Duolingo wishes it could be. I finally understand
                German grammar — after 3 years of struggling."
            </p>

            <div className="flex items-center gap-3">
                <div className="flex h-7 w-7 items-center justify-center rounded-full bg-indigo-500 text-xs font-bold">
                    ES
                </div>

                <span className="text-xs text-white/60">
                    Emma S. · Language Teacher, Berlin
                </span>
            </div>
        </div>
    );
}

function LoginStats() {
    return (
        <div className="relative z-10 flex gap-10 mt-[5vh] mb-[-4vh]">
            {STATS.map((stat) => (
                <div key={stat.label}>
                    <div className="text-2xl font-bold">
                        {stat.value}
                    </div>

                    <div className="text-xs text-white/60">
                        {stat.label}
                    </div>
                </div>
            ))}
        </div>
    );
}