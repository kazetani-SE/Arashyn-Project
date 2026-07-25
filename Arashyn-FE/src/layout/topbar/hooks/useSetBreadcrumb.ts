import { useEffect } from "react";
import { useBreadcrumbContext } from "@/layout/topbar/contexts/BreadcrumbContext.tsx";

export function useSetBreadcrumb(title: string, href: string) {
    const { pushOrJump } = useBreadcrumbContext();

    useEffect(() => {
        pushOrJump({ title, href });
    }, [title, href, pushOrJump]);
}