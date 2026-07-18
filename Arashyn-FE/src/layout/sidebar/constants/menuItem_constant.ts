import { Home, Search, Users, Settings } from "lucide-react";
import type {MenuItem} from "@/layout/sidebar/types/menuItem.ts";
import {ROUTE_PATHS} from "@/app/router/route.ts";

export const MENUS: MenuItem[] = [
    {
        label: "Dashboard",
        path: ROUTE_PATHS.DASHBOARD,
        icon: Home,
    },
    {
        label: "Discover",
        path: ROUTE_PATHS.DISCOVER,
        icon: Search,
    },
    {
        label: "Community",
        path: ROUTE_PATHS.COMMUNITY,
        icon: Users,
    },
    {
        label: "Settings",
        path: ROUTE_PATHS.SETTINGS,
        icon: Settings,
    },
];