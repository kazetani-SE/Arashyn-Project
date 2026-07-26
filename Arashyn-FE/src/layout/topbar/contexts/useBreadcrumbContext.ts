import {useContext} from "react";
import {BreadcrumbContext} from "@/layout/topbar/contexts/BreadcrumbContext.tsx";

export function useBreadcrumbContext() {
    const ctx = useContext(BreadcrumbContext);
    if (!ctx) throw new Error("useBreadcrumbContext must be used within BreadcrumbProvider");
    return ctx;
}