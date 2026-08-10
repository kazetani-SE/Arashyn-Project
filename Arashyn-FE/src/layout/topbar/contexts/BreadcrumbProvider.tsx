import { type ReactNode, useCallback, useState } from "react";
import { GRAMMAR_DETAIL_BREADCRUMB } from "@/layout/topbar/constants/topbar_constants.ts";
import type { BreadcrumbItem } from "@/layout/topbar/types/breadcum_types.ts";
import { BreadcrumbContext } from "./BreadcrumbContext";
import {MENUS} from "@/layout/sidebar/constants/menuItem_constant.ts";

const ROOT_PATHS = new Set(MENUS.map((m) => m.path));

export function BreadcrumbProvider({ children }: { children: ReactNode }) {
    const [items, setItems] = useState<BreadcrumbItem[]>(GRAMMAR_DETAIL_BREADCRUMB);

    const pushOrJump = useCallback((item: BreadcrumbItem) => {
        setItems((prev) => {
            if (ROOT_PATHS.has(item.href)) {
                return [item];
            }

            const itemKey = item.key ?? item.href;
            const existingIndex = prev.findIndex((i) => (i.key ?? i.href) === itemKey);

            if (existingIndex !== -1) {
                const next = prev.slice(0, existingIndex + 1);
                next[existingIndex] = item; // update tại chỗ, href được update thành giá trị mới nhất
                return next;
            }

            return [...prev, item];
        });
    }, []);

    const reset = useCallback((newItems: BreadcrumbItem[] = GRAMMAR_DETAIL_BREADCRUMB) => {
        setItems(newItems);
    }, []);

    return (
        <BreadcrumbContext.Provider value={{ items, pushOrJump, reset }}>
            {children}
        </BreadcrumbContext.Provider>
    );
}