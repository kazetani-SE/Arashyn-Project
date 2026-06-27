import { MessageSquare, Star } from "lucide-react";
import { TESTIMONIALS } from "@/features/home/constants/testimonial_constant";

export default function TestimonialsSection() {
    return (
        <section className="relative z-10 w-full py-32 mt-[-15vh]">
            <div className="container mx-auto px-6">
                <SectionHeading />

                <div className="grid gap-6 lg:grid-cols-3">
                    {TESTIMONIALS.map((item) => (
                        <TestimonialCard key={item.name} {...item} />
                    ))}
                </div>
            </div>
        </section>
    );
}

function SectionHeading() {
    return (
        <div className="mb-16 text-center">
            <div className="border-indigo-500/20 bg-indigo-500/10 text-indigo-400 inline-flex items-center gap-2 rounded-full border px-4 py-2 text-xs font-semibold uppercase tracking-[0.2em]">
                <MessageSquare className="size-3" />
                Testimonials
            </div>
            <h2 className="mt-6 text-5xl font-bold text-white">
                Loved by language learners worldwide.
            </h2>
        </div>
    );
}

type TestimonialCardProps = {
    name: string;
    role: string;
    city: string;
    avatar: string;
    text: string;
};

function TestimonialCard({
                             name,
                             role,
                             city,
                             avatar,
                             text,
                         }: TestimonialCardProps) {
    return (
        <div className="border-white/10 bg-white/[0.03] rounded-3xl border p-6 backdrop-blur-xl">
            <div className="mb-4 flex gap-1">
                {Array.from({ length: 5 }).map((_, index) => (
                    <Star
                        key={index}
                        className="size-4 fill-yellow-400 text-yellow-400"
                    />
                ))}
            </div>

            <p className="text-white/60 mb-6 text-sm leading-relaxed">"{text}"</p>

            <div className="flex items-center gap-3">
                <div className="flex size-10 items-center justify-center rounded-full bg-indigo-500 text-xs font-bold text-white">
                    {avatar}
                </div>

                <div>
                    <p className="text-sm font-semibold text-white">{name}</p>
                    <p className="text-white/50 text-xs">
                        {role} · {city}
                    </p>
                </div>
            </div>
        </div>
    );
}