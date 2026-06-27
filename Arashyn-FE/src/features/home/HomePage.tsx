import HeroSection from "@/features/home/components/HeroSection.tsx";
import FeatureSection from "@/features/home/components/FeatureSection.tsx";
import StatsSection from "@/features/home/components/StatsSection.tsx";
import StreakSection from "@/features/home/components/StreakSection.tsx";
import HowItWorksSection from "@/features/home/components/HowItWorkSection.tsx";
import TestimonialsSection from "@/features/home/components/TestimonialsSection.tsx";
import CTASection from "@/features/home/components/CTASection.tsx";
import FAQSection from "@/features/home/components/FAQSection.tsx";
import {L_FAQ} from "@/features/home/constants/FAQ_constant.ts";

export default function HomePage() {
    return (
        <div className="flex flex-col min-h-screen items-center justify-center
         relative overflow-hidden gap-0">
            <PageBackground />

            <HeroSection />

            <StatsSection />

            <FeatureSection />

            <StreakSection />

            <HowItWorksSection />

            <TestimonialsSection />

            <FAQSection items={L_FAQ} />

            <CTASection />
        </div>
    );
}

function PageBackground() {
    return (
        <>
            <div className="bg-indigo-500/15 absolute left-1/2 top-[-20vh] h-[1800px] w-[1800px] -translate-x-1/2 rounded-full blur-[280px]" />
            <div className="bg-violet-500/10 absolute left-[-20%] top-[35%] h-[1200px] w-[1200px] rounded-full blur-[250px]" />
            <div className="bg-fuchsia-500/10 absolute right-[-10%] top-[70%] h-[1000px] w-[1000px] rounded-full blur-[220px]" />

            <div className="bg-cyan-500/10 absolute right-[5%] top-[5%] h-[900px] w-[900px] rounded-full blur-[230px]" />
            <div className="bg-rose-500/8 absolute left-[10%] top-[80%] h-[1100px] w-[1100px] rounded-full blur-[260px]" />
            <div className="bg-blue-500/10 absolute left-[60%] top-[55%] h-[800px] w-[800px] rounded-full blur-[200px]" />
            <div className="bg-purple-500/8 absolute left-[-10%] top-[5%] h-[700px] w-[700px] rounded-full blur-[210px]" />

            <div className="absolute inset-0 bg-[radial-gradient(circle_at_top,rgba(99,102,241,0.15),transparent_60%)]" />
        </>
    );
}