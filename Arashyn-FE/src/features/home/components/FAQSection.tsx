import type {FAQSectionProps} from "@/features/home/types/FAQ_type.ts";
import {useState} from "react";
import {ChevronDown, MessageCircle} from "lucide-react";

export default function FAQSection({
                                       items,
                                       title = "Common questions.",
                                   }: FAQSectionProps) {
    const [open, setOpen] = useState<number | null>(null);

    return (
        <section className="relative w-full py-32 mt-[-15vh]">
            <div className="container mx-auto px-6">
                <div className="mb-14 text-center">
                    <div className="border-indigo-500/20 bg-indigo-500/10 text-indigo-400 inline-flex items-center gap-2 rounded-full border px-4 py-2 text-xs font-semibold uppercase tracking-[0.2em]">
                        <MessageCircle className="size-3" />
                        FAQ
                    </div>
                    <h2 className="mt-6 text-4xl font-bold md:text-5xl">{title}</h2>
                </div>

                <div className="mx-auto flex max-w-3xl flex-col gap-3">
                    {items.map((item, index) => (
                        <div
                            key={index}
                            className="border-white/10 bg-white/[0.03] rounded-3xl border overflow-hidden backdrop-blur-xl"
                        >
                            <button
                                onClick={() => setOpen(open === index ? null : index)}
                                className="flex w-full items-center justify-between px-6 py-5 text-left"
                            >
                                <span className="font-medium text-white">{item.q}</span>
                                <ChevronDown
                                    className={`size-5 text-white/50 transition-transform duration-200 ${
                                        open === index ? "rotate-180" : ""
                                    }`}
                                />
                            </button>

                            {open === index && (
                                <div className="text-white/60 px-6 pb-5 text-sm leading-relaxed">
                                    {item.a}
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
}