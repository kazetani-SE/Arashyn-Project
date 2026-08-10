import {FeatureBackground} from "@/components/background/FeatureBackground.tsx";
import {useSetBreadcrumb} from "@/layout/topbar/hooks/useSetBreadcrumb.ts";
import {ROUTE_PATHS, ROUTES} from "@/app/router/route.ts";
import {useParams} from "react-router-dom";

export default function DetailPage() {

    const { grammarId } = useParams<{ grammarId: string }>();
    useSetBreadcrumb("Detail", grammarId ? ROUTES.grammarDetail(grammarId) : ROUTE_PATHS.DETAIL);

    return (
        <div>
            <FeatureBackground />

            <h1>Detail Page</h1>
        </div>
    )
}