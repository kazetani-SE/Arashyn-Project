import { createContext} from "react";
import type {BreadcrumbContextType} from "@/layout/topbar/types/breadcum_types.ts";

export const BreadcrumbContext = createContext<BreadcrumbContextType | null>(null);

