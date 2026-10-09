import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";
import { MENUS } from "@/layout/sidebar/constants/menuItem_constant.ts";
import type { BreadcrumbItem } from "@/layout/topbar/types/breadcum_types.ts";

const ROOT_PATHS = new Set(MENUS.map((m) => m.path));
const MAX_ITEMS = 8;

export const breadcrumbKey = (item: BreadcrumbItem) =>
    `${item.title}::${item.key ?? ""}`;

type BreadcrumbState = {
    items: BreadcrumbItem[];
    pushOrJump: (item: BreadcrumbItem) => void;
    reset: (items?: BreadcrumbItem[]) => void;
};

export const useBreadcrumbStore = create<BreadcrumbState>()(
    persist(
        (set) => ({
            items: [],

            pushOrJump: (item) =>
                set((state) => {
                    if (ROOT_PATHS.has(item.href)) {
                        return { items: [item] };
                    }

                    const key = breadcrumbKey(item);
                    const index = state.items.findIndex((i) => breadcrumbKey(i) === key);

                    if (index !== -1) {
                        if (index === state.items.length - 1 && state.items[index].href === item.href) {
                            return state;
                        }
                        const next = state.items.slice(0, index + 1);
                        next[index] = item;
                        return { items: next };
                    }

                    return { items: [...state.items, item].slice(-MAX_ITEMS) };
                }),

            reset: (items = []) => set({ items }),
        }),
        {
            name: "breadcrumb",
            version: 1,
            storage: createJSONStorage(() => sessionStorage),
            partialize: (state) => ({ items: state.items }),
        }
    )
);