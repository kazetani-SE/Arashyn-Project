import { Globe, ChevronDown, Plus } from "lucide-react";
import { useState } from "react";
import {LANGUAGES, QUICK_CREATE_ACTIONS} from "@/layout/topbar/constants/topbar_constants";

export default function TopBarActions() {
    const [showLanguage, setShowLanguage] = useState(false);
    const [showCreate, setShowCreate] = useState(false);

    const [language, setLanguage] = useState("Japanese");

    return (
        <div className="flex items-center gap-3">
            <div className="relative">
                <button
                    onClick={() => {
                        setShowCreate((v) => !v);
                        setShowLanguage(false);
                    }}
                    className="flex items-center gap-2 px-3 py-2 rounded-xl border border-[#27324B] hover:bg-white/5 transition"
                >
                    <Plus size={14} />
                    <span>New</span>
                    <ChevronDown size={14} />
                </button>

                {showCreate && (
                    <div className="absolute right-0 mt-2 w-48 rounded-xl bg-[#161E33] border border-[#27324B] shadow-xl overflow-hidden z-50">
                        {QUICK_CREATE_ACTIONS.map((action) => (
                            <button
                                key={action.title}
                                className="w-full px-4 py-2.5 text-left hover:bg-white/5 transition"
                                onClick={() => {
                                    action.onClick?.();
                                    setShowCreate(false);
                                }}
                            >
                                {action.title}
                            </button>
                        ))}
                    </div>
                )}
            </div>

            {/* Language */}
            <div className="relative">
                <button
                    onClick={() => {
                        setShowLanguage((v) => !v);
                        setShowCreate(false);
                    }}
                    className="flex items-center gap-2 px-3 py-2 rounded-xl border border-[#27324B] hover:bg-white/5 transition"
                >
                    <Globe size={14} />
                    {language}
                    <ChevronDown size={14} />
                </button>

                {showLanguage && (
                    <div className="absolute right-0 mt-2 w-44 rounded-xl bg-[#161E33] border border-[#27324B] shadow-xl overflow-hidden z-50">
                        {LANGUAGES.map((lang) => (
                            <button
                                key={lang}
                                className={`w-full px-4 py-2.5 text-left hover:bg-white/5 transition ${
                                    language === lang ? "text-indigo-400" : ""
                                }`}
                                onClick={() => {
                                    setLanguage(lang);
                                    setShowLanguage(false);
                                }}
                            >
                                {lang}
                            </button>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}