import {FEATURES} from "@/features/home/constants/feature_constant.ts";
import {Sparkles} from "lucide-react";

export default function FeatureSection() {
    return (
        <section className="py-22 relative z-10 w-full">
            <div className="flex flex-col justify-start items-center container mx-auto px-6">

                <div className="border-indigo-500/20 bg-indigo-500/10 text-indigo-400 inline-flex items-center gap-2
                rounded-full border px-4 py-2 text-xs font-semibold uppercase tracking-[0.2em] mb-[4vh]">
                    <Sparkles className="size-3" />
                    Features
                </div>

                <SectionHeading />

                <FeatureGrid />

            </div>
        </section>
    );
}

function SectionHeading() {
    return (
        <div className="mx-auto mb-16 max-w-3xl text-center">
            <h2 className="text-4xl font-bold md:text-5xl">
                Learn Grammar Like a Linguist
            </h2>
            <p className="text-muted-foreground mt-6">
                Built around concepts, structure, and understanding — not memorization.
            </p>
        </div>
    );
}

function FeatureGrid() {
    return (
        <div className="grid gap-4 md:grid-cols-3">
            {FEATURES.map((feature) => {
                const Icon = feature.icon;

                return (
                    <FeatureCard
                        key={feature.id}
                        title={feature.title}
                        description={feature.description}
                        icon={<Icon className="size-6" />}
                    />
                );
            })}
        </div>
    );
}

type FeatureCardProps = {
    title: string;
    description: string;
    icon: React.ReactNode;
};

function FeatureCard({ title, description, icon }: FeatureCardProps) {
    return (
        <div className="border-white/10 bg-white/[0.03] rounded-3xl border p-8 backdrop-blur-xl">
            <div className="bg-indigo-500/15 text-indigo-400 mb-6 flex size-12 items-center justify-center rounded-xl">
                {icon}
            </div>
            <h3 className="mb-3 text-xl font-semibold text-white">{title}</h3>
            <p className="text-white/60">{description}</p>
        </div>
    );
}