import { createContext, useContext, useState, useCallback, type ReactNode } from "react";
import type { BreadcrumbProps } from "@/layout/topbar/types/topbar_types.ts";
import {GRAMMAR_DETAIL_BREADCRUMB} from "@/layout/topbar/constants/topbar_constants.ts";

type BreadcrumbItem = BreadcrumbProps["items"][number];

type BreadcrumbContextType = {
    items: BreadcrumbItem[];
    pushOrJump: (item: BreadcrumbItem) => void;
    reset: (items?: BreadcrumbItem[]) => void;
};

const BreadcrumbContext = createContext<BreadcrumbContextType | null>(null);

export function BreadcrumbProvider({ children }: { children: ReactNode }) {
    const [items, setItems] = useState<BreadcrumbItem[]>(GRAMMAR_DETAIL_BREADCRUMB);

    const pushOrJump = useCallback((item: BreadcrumbItem) => {
        setItems((prev) => {
            const existingIndex = prev.findIndex((i) => i.href === item.href);

            if (existingIndex !== -1) {
                // đã có trong breadcrumb -> nhảy về vị trí đó (cắt phần sau)
                const next = prev.slice(0, existingIndex + 1);
                // cập nhật lại title mới nhất (phòng khi title động, vd có id/tên)
                next[existingIndex] = item;
                return next;
            }

            // chưa có -> thêm vào cuối
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

export function useBreadcrumbContext() {
    const ctx = useContext(BreadcrumbContext);
    if (!ctx) throw new Error("useBreadcrumbContext must be used within BreadcrumbProvider");
    return ctx;
}