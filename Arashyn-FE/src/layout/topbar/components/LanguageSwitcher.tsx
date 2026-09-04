import { Globe, ChevronDown, Plus } from "lucide-react";
import { useState } from "react";
import {LANGUAGE_LABELS, LANGUAGE_OPTIONS, useQuickCreateActions} from "@/layout/topbar/constants/topbar_constants";
import {useLanguageStore} from "@/shared/store/language_store.ts";

export default function TopBarActions() {
    const [showLanguage, setShowLanguage] = useState(false);
    const [showCreate, setShowCreate] = useState(false);

    const language = useLanguageStore((s) => s.language);
    const setLanguage = useLanguageStore((s) => s.setLanguage);

    const quickCreateAction = useQuickCreateActions();

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
                        {quickCreateAction.map((action) => (
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
                    {LANGUAGE_LABELS[language]}
                    <ChevronDown size={14} />
                </button>

                {showLanguage && (
                    <div className="absolute right-0 mt-2 w-44 rounded-xl bg-[#161E33] border border-[#27324B] shadow-xl overflow-hidden z-50">
                        {LANGUAGE_OPTIONS.map((code) => (
                            <button
                                key={code}
                                className={`w-full px-4 py-2.5 text-left hover:bg-white/5 transition ${
                                    language === code ? "text-indigo-400" : ""
                                }`}
                                onClick={() => {
                                    setLanguage(code);
                                    setShowLanguage(false);
                                }}
                            >
                                {LANGUAGE_LABELS[code]}
                            </button>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}