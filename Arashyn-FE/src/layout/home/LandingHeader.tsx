import { BookOpen } from "lucide-react";
import {ROUTE_PATHS} from "@/app/router/route.ts";
import {useNavigate} from "react-router-dom";

export default function LandingHeader() {
    const nav = useNavigate();

    const loginHandler = () => {
        nav(ROUTE_PATHS.LOGIN);
    }

    return (
        <header className="fixed inset-x-0 top-0 z-50 border-b border-white/10
        bg-[#070b1ae0] backdrop-blur-2xl bg-background/50">
            <div className="container mx-auto flex items-center justify-between px-6 py-4">
                {/* Logo */}
                <button className="flex items-center gap-3">
                    <div className="border-white/10 bg-indigo-500/10 flex h-8 w-8 items-center justify-center rounded-lg border">
                        <BookOpen className="size-4 text-indigo-400" />
                    </div>
                    <span className="text-lg font-bold tracking-tight">Arashyn</span>
                </button>

                {/* Navigation */}
                <nav className="hidden items-center gap-8 md:flex">
                    <button className="text-sm text-indigo-400">Features</button>
                    <button className="text-white/60 hover:text-white text-sm">
                        About
                    </button>
                    <button className="text-white/60 hover:text-white text-sm">
                        Support
                    </button>
                    <button className="text-white/60 hover:text-white text-sm">
                        Pricing
                    </button>
                </nav>

                {/* Actions */}
                <div className="flex items-center gap-3">
                    <button
                        className="text-white/60 px-4 py-2 text-sm hover:text-white cursor-pointer"
                        onClick={loginHandler}
                    >
                        Log in
                    </button>
                    <button className="bg-indigo-500 shadow-[0_0_24px_rgba(99,102,241,0.4)]
                    rounded-xl px-5 py-2 text-sm font-semibold text-white hover:bg-indigo-600 cursor-pointer">
                        Get Started
                    </button>
                </div>
            </div>
        </header>
    );
}