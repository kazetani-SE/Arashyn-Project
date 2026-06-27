import {
    BookOpen,
    Globe,
} from "lucide-react";

import {
    FaGithub,
    FaLinkedin,
} from "react-icons/fa";

import {
    FaXTwitter,
} from "react-icons/fa6";

const footerLinks = [
    {
        title: "Product",
        links: [
            "Features",
            "Pricing",
            "AI Mentor",
            "Grammar Library",
            "Changelog",
        ],
    },
    {
        title: "Company",
        links: [
            "About",
            "Blog",
            "Careers",
            "Press Kit",
            "Support Us",
        ],
    },
    {
        title: "Resources",
        links: [
            "Documentation",
            "API",
            "Status",
            "Community",
            "Open Source",
        ],
    },
];

export default function LandingFooter() {
    return (
        <footer className="border-t border-white/10 pt-16 pb-8">
            <div className="container mx-auto px-6">
                <div className="mb-12 grid gap-12 lg:grid-cols-5">
                    {/* Brand */}
                    <div className="lg:col-span-2">
                        <div className="mb-4 flex items-center gap-3">
                            <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-indigo-500/10">
                                <BookOpen className="size-4 text-indigo-400" />
                            </div>
                            <span className="text-lg font-bold">Arashyn</span>
                        </div>

                        <p className="mb-6 max-w-sm text-sm leading-relaxed text-white/60">
                            AI-powered grammar learning for curious minds. Master any
                            language's structure — one rule at a time.
                        </p>

                        <div className="flex items-center gap-3">
                            {[FaGithub, FaLinkedin, FaXTwitter, Globe].map((Icon, index) => (
                                <button
                                    key={index}
                                    className="border-white/10 bg-white/[0.03] flex h-8 w-8 items-center justify-center rounded-lg border"
                                >
                                    <Icon className="size-4 text-white/60" />
                                </button>
                            ))}
                        </div>
                    </div>

                    {/* Links */}
                    {footerLinks.map((section) => (
                        <div key={section.title}>
                            <h4 className="text-white/50 mb-4 text-[10px] font-bold uppercase tracking-[0.2em]">
                                {section.title}
                            </h4>

                            <div className="flex flex-col gap-3">
                                {section.links.map((link) => (
                                    <button
                                        key={link}
                                        className="text-white/60 hover:text-white text-sm text-left"
                                    >
                                        {link}
                                    </button>
                                ))}
                            </div>
                        </div>
                    ))}
                </div>

                {/* Bottom */}
                <div className="border-t border-white/10 text-white/50 pt-8 text-xs flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
                    <span>© 2026 Arashyn. All rights reserved.</span>

                    <div className="flex items-center gap-6">
                        <button>Privacy Policy</button>
                        <button>Terms of Service</button>
                        <button>Cookie Settings</button>
                    </div>
                </div>
            </div>
        </footer>
    );
}