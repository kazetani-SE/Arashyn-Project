import { useMemo } from "react";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { useQueryClient } from "@tanstack/react-query";
import { ArrowLeft } from "lucide-react";
import { Button } from "@/components/ui/button.tsx";
import { FeatureBackground } from "@/components/background/FeatureBackground.tsx";
import { ROUTE_PATHS, ROUTES } from "@/app/router/route.ts";
import { LEARNING_TYPES, type LearningType } from "@/features/learning/constants/learning_types.ts";
import { FlashcardPart } from "@/features/learning/part/FlashcardPart.tsx";
import { useGrammarSummaryList } from "@/shared/hook/grammar_component_build.ts";
import type { UserDeckDetailResponse } from "@/lib/api/generated";
import {FillBlankPart} from "@/features/learning/part/FillBlankPart.tsx";

const isLearningType = (v?: string): v is LearningType =>
    Object.values(LEARNING_TYPES).includes(v as LearningType);

export function LearningPage() {
    const { deckId = "", type } = useParams<{ deckId: string; type: string }>();
    const queryClient = useQueryClient();
    const navigate = useNavigate();

    const deck = useMemo(
        () => queryClient.getQueryData<UserDeckDetailResponse>(["user-container", "deck", deckId]),
        [queryClient, deckId]
    );

    const items = deck?.grammars?.items ?? [];
    const grammars = useGrammarSummaryList(
        items as Parameters<typeof useGrammarSummaryList>[0]
    );

    const goBack = () => {
        if (window.history.state?.idx > 0) navigate(-1);
        else navigate(ROUTE_PATHS.HUB);
    };

    if (!deck) {
        return <Navigate to={ROUTES.userDeckDetail(deckId)} replace />;
    }

    if (!isLearningType(type)) {
        return <p className="py-8 text-center text-muted-foreground">Unknown learning type.</p>;
    }

    const renderPart = () => {
        switch (type) {
            case LEARNING_TYPES.FLASHCARD:
                return <FlashcardPart key={deckId} grammars={grammars} />;
            case LEARNING_TYPES.FILL_BLANK:
                return <FillBlankPart key={deckId} deckId={deckId} />;
            default:
                return null;
        }
    };

    return (
        <div className="relative flex min-h-[85vh] flex-col px-4 py-6 sm:px-8 sm:py-8">
            <FeatureBackground />

            <div>
                <Button
                    variant="ghost"
                    onClick={goBack}
                    className="-ml-3 gap-1.5 text-muted-foreground hover:text-[#a5adf0]"
                >
                    <ArrowLeft className="h-4 w-4" />
                    Back
                </Button>
            </div>

            <div className="flex flex-1 items-center justify-center py-6">
                {renderPart()}
            </div>
        </div>
    );
}