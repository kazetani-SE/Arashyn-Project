import {STEPS} from "@/features/home/constants/hiw_constant.ts";

export default function HowItWorksSection() {
    return (
        <section className="relative z-10 w-full py-32 mt-[-15vh]">
            <div className="container mx-auto px-6">
                <div className="mb-16 text-center">
                    <div className="border-indigo-500/20 bg-indigo-500/10 text-indigo-400 inline-flex items-center rounded-full border px-4 py-2 text-xs font-semibold uppercase tracking-[0.2em]">
                        How It Works
                    </div>
                    <h2 className="mt-6 text-5xl font-bold text-white">
                        Simple. Structured. Effective.
                    </h2>
                </div>

                <div className="grid gap-6 lg:grid-cols-5">
                    {STEPS.map((step) => (
                        <div
                            key={step.number}
                            className="border-white/10 bg-white/[0.03] rounded-3xl border p-6 backdrop-blur-xl"
                        >
                            <div className="text-indigo-500/50 mb-4 text-4xl font-extrabold">
                                {step.number}
                            </div>
                            <h3 className="text-lg font-semibold text-white mb-3">
                                {step.title}
                            </h3>
                            <p className="text-white/60 text-sm leading-relaxed">
                                {step.description}
                            </p>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
}