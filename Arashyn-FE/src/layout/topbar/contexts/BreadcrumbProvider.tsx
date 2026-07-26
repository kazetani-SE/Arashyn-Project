import {type ReactNode, useCallback, useState} from "react";
import {GRAMMAR_DETAIL_BREADCRUMB} from "@/layout/topbar/constants/topbar_constants.ts";
import type {BreadcrumbItem} from "@/layout/topbar/types/breadcum_types.ts";
import { BreadcrumbContext } from "./BreadcrumbContext";

export function BreadcrumbProvider({ children }: { children: ReactNode }) {
    const [items, setItems] = useState<BreadcrumbItem[]>(GRAMMAR_DETAIL_BREADCRUMB);

    const pushOrJump = useCallback((item: BreadcrumbItem) => {
        setItems((prev) => {
            const existingIndex = prev.findIndex((i) => i.href === item.href);

            if (existingIndex !== -1) {
                const next = prev.slice(0, existingIndex + 1);
                next[existingIndex] = item;
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
