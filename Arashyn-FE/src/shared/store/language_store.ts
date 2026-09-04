import { create } from "zustand";
import { persist } from "zustand/middleware";
import {Language} from "@/shared/enum/language.ts";

type LanguageState = {
    language: Language;
    setLanguage: (lang: Language) => void;
}

export const useLanguageStore = create<LanguageState>()(
    persist(
        (set) => ({
            language: Language.JA,
            setLanguage: (lang) => set({ language: lang }),
        }),
        { name: "lang-settings" }
    )
);