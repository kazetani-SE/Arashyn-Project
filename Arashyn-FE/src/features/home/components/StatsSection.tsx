import {STATS} from "@/features/home/constants/stats_constant.ts";

export default function StatsSection() {
    return (
        <section
            className="
                relative z-10
                border-y border-white/10
                bg-[#0d122980]
                backdrop-blur-sm
                w-full
            "
        >
            <div className="container mx-auto px-6">
                <div className="grid grid-cols-2 gap-8 py-10 md:grid-cols-5">
                    {STATS.map((item) => (
                        <div
                            key={item.label}
                            className="text-center"
                        >
                            <h3 className="text-4xl font-bold text-white">
                                {item.value}
                            </h3>

                            <p className="mt-2 text-sm text-white/50">
                                {item.label}
                            </p>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
}