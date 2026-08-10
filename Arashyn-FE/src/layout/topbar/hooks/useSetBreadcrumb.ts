import { useEffect } from "react";
import { useBreadcrumbContext } from "@/layout/topbar/contexts/useBreadcrumbContext.ts";

export function useSetBreadcrumb(title: string, href: string, key?: string) {
    const { pushOrJump } = useBreadcrumbContext();

    useEffect(() => {
        pushOrJump({ title, href, key });
    }, [title, href, key, pushOrJump]);
}