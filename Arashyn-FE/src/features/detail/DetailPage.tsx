import { FeatureBackground } from "@/components/background/FeatureBackground.tsx";
import { useSetBreadcrumb } from "@/layout/topbar/hooks/useSetBreadcrumb.ts";
import { ROUTE_PATHS, ROUTES } from "@/app/router/route.ts";
import { useNavigate, useParams } from "react-router-dom";
import { ContentPart } from "@/features/detail/parts/ContentPart.tsx";
import { SummarizePart } from "@/features/detail/parts/SummarizePart.tsx";
import { ArrowLeft } from "lucide-react";
import { Button } from "@/components/ui/button.tsx";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { useEffect } from "react";
import {useGrammarDetail} from "@/features/detail/hook/use_grammar_detail.ts";

export default function DetailPage() {
    const { grammarId } = useParams<{ grammarId: string }>();
    const navigate = useNavigate();

    useSetBreadcrumb(
        "Detail",
        grammarId ? ROUTES.grammarDetail(grammarId) : ROUTE_PATHS.DETAIL
    );

    const { data, isLoading, error } = useGrammarDetail(grammarId);

    console.log("[DETAIL]", {
        grammarId,
        dataId: data?.id,
        dataTitle: data?.title,
        isLoading,
        error,
    });

    const isNotFound = error?.code === "NOT_FOUND";

    useEffect(() => {
        if (!grammarId) {
            navigate(ROUTE_PATHS.DISCOVER, { replace: true });
        }
    }, [grammarId, navigate]);

    const onBack = () => {
        navigate(ROUTE_PATHS.DISCOVER, { replace: true });
    };

    if (!grammarId) return null;

    return (
        <div>
            <FeatureBackground />

            <div className="mx-auto max-w-5xl px-6 py-10">
                <div className="mb-4">
                    <Button variant="ghost" className="gap-2" onClick={onBack}>
                        <ArrowLeft className="h-4 w-4" />
                        Back to Discover
                    </Button>
                </div>

                {isLoading && <DetailSkeleton />}

                {!isLoading && isNotFound && (
                    <div className="py-12 text-center text-muted-foreground">
                        Grammar not found.
                    </div>
                )}

                {!isLoading && error && !isNotFound && (
                    <div className="py-12 text-center text-muted-foreground">
                        Something went wrong while loading. Please try again.
                    </div>
                )}

                {!isLoading && !error && data && (
                    <div className="grid grid-cols-1 gap-10 md:grid-cols-[220px_1fr]">
                        <SummarizePart data={data} />
                        <ContentPart data={data} />
                    </div>
                )}
            </div>
        </div>
    );
}

function DetailSkeleton() {
    return (
        <div className="grid grid-cols-1 gap-10 md:grid-cols-[220px_1fr]">
            <div className="space-y-3">
                <Skeleton className="h-6 w-2/3" />
                <Skeleton className="h-4 w-full" />
                <Skeleton className="h-4 w-5/6" />
            </div>
            <div className="space-y-4">
                <Skeleton className="h-8 w-1/2" />
                <Skeleton className="h-24 w-full" />
                <Skeleton className="h-24 w-full" />
            </div>
        </div>
    );
}