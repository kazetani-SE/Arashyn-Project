import { ArrowRight } from "lucide-react";

export default function CTASection() {
    return (
        <section className="relative z-10 overflow-hidden py-36 mt-[-20vh]">
            <div className="absolute left-1/2 top-1/2 h-[500px] w-[900px] -translate-x-1/2 -translate-y-1/2 rounded-full bg-0 blur-[180px]" />

            <div className="relative mx-auto max-w-3xl px-6 text-center">
                <div className="mb-6 text-6xl">🎯</div>

                <h2 className="mb-6 text-5xl font-extrabold text-white md:text-6xl">
                    Start your grammar journey today.
                </h2>

                <p className="mx-auto mb-10 max-w-2xl text-lg text-white/60">
                    Join 12,000+ language learners who finally understand grammar — not
                    just memorize it.
                </p>

                <button className="bg-indigo-500 shadow-[0_0_60px_rgba(99,102,241,0.45)] inline-flex items-center gap-2 rounded-xl px-10 py-4 text-lg font-bold text-white">
                    Get Started Free
                    <ArrowRight className="size-5" />
                </button>

                <p className="mt-5 text-sm text-white/50">
                    unlimited free trial · No credit card · Cancel anytime
                </p>
            </div>
        </section>
    );
}