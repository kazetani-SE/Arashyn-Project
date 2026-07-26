import {FeatureBackground} from "@/components/background/FeatureBackground.tsx";
import {useSetBreadcrumb} from "@/layout/topbar/hooks/useSetBreadcrumb.ts";
import {ROUTE_PATHS} from "@/app/router/route.ts";

export default function DashboardPage() {
    useSetBreadcrumb("Dashboard", ROUTE_PATHS.DASHBOARD);

    return (
        <div>

            <FeatureBackground />

        </div>
    );
}