import {FeatureBackground} from "@/components/background/FeatureBackground.tsx";
import {useSetBreadcrumb} from "@/layout/topbar/hooks/useSetBreadcrumb.ts";
import {ROUTE_PATHS} from "@/app/router/route.ts";

export default function CommunityPage() {
    useSetBreadcrumb("Community", ROUTE_PATHS.COMMUNITY);

    return(
        <div>

            <FeatureBackground/>

        </div>
    );
}