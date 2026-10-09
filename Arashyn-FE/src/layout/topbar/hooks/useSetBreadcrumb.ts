import { useEffect } from "react";
import {useBreadcrumbStore} from "@/shared/store/breadcrumb_store.ts";

export function useSetBreadcrumb(title: string | undefined, href: string, key?: string) {
    const pushOrJump = useBreadcrumbStore((s) => s.pushOrJump);

    useEffect(() => {
        if (!title) return;
        pushOrJump({ title, href, key });
    }, [title, href, key, pushOrJump]);
}