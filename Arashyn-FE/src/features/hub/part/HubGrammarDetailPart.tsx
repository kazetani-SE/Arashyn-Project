import { useNavigate } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { Button } from "@/components/ui/button.tsx";
import { ROUTE_PATHS, ROUTES } from "@/app/router/route.ts";
import { useUserGrammarDetail } from "@/features/hub/hook/use_user_grammar_detail.ts";

type Props = {
    id: string;
};

export function HubGrammarDetailPart({ id }: Props) {
    const navigate = useNavigate();
    const { data, isLoading, isError } = useUserGrammarDetail(id);

    const goBack = () => {
        if (window.history.state?.idx > 0) {
            navigate(-1);
        } else {
            navigate(ROUTE_PATHS.HUB);
        }
    };

    if (isLoading) {
        return (
            <div className="space-y-4">
                <Skeleton className="h-6 w-24 rounded-full" />
                <Skeleton className="h-10 w-1/2" />
                <Skeleton className="h-4 w-2/3" />
            </div>
        );
    }

    if (isError || !data) {
        return (
            <p className="py-8 text-center text-muted-foreground">
                Something went wrong while loading. Please try again.
            </p>
        );
    }

    return (
        <>
            <div className="flex items-center justify-between mt-[1vh] mb-[1vh]">
                <Button
                    variant="ghost"
                    onClick={goBack}
                    className="-ml-3 mb-2 gap-1.5 text-muted-foreground hover:text-[#a5adf0]"
                >
                    <ArrowLeft className="h-4 w-4" />
                    Back
                </Button>
            </div>

            {/* Header */}
            <div className="space-y-3">
                <div className="flex flex-wrap items-center gap-3">
                    <h1 className="text-3xl font-bold tracking-tight">{data.title}</h1>

                    {data.proficiency && (
                        <span className="rounded-full bg-muted px-2.5 py-0.5 text-xs font-semibold uppercase">
                          {data.proficiency}
                        </span>
                    )}

                    {data.language && (
                        <span className="rounded-full bg-muted px-2.5 py-0.5 text-xs font-medium">
                          {data.language}
                        </span>
                    )}
                </div>

                <div className="flex flex-wrap items-center gap-3 text-sm text-muted-foreground">
                    {data.grammarId && (
                        <button
                            onClick={() => navigate(ROUTES.grammarDetail(data.grammarId!))}
                            className="text-[#a5adf0] hover:underline"
                        >
                            View original grammar
                        </button>
                    )}

                    {data.lastReviewAt && (
                        <span>
                          Last review: {new Date(data.lastReviewAt).toLocaleString()}
                        </span>
                    )}
                </div>

                {data.filters && data.filters.length > 0 && (
                    <div className="flex flex-wrap gap-1.5">
                        {data.filters.map((f) => (
                            <span
                                key={f.id}
                                className="rounded-full bg-muted px-2.5 py-1 text-xs font-medium text-muted-foreground"
                            >
                          {f.name}
                        </span>
                        ))}
                    </div>
                )}
            </div>

            {/* Groups */}
            <div className="space-y-8">
                {data.groups?.map((group) => (
                    <div key={group.groupKey} className="space-y-4 rounded-xl border border-border p-5">
                        {/* Components / Pattern */}
                        <div className="flex flex-wrap items-center justify-center gap-2 text-xl font-medium">
                            {group.components
                                ?.sort((a, b) => (a.order ?? 0) - (b.order ?? 0))
                                .map((c, idx) => (
                                    <span key={idx} className={c.optional ? "opacity-60" : ""}>
                    {c.keyword}
                                        {c.optional && <span className="text-xs ml-1">(opt)</span>}
                  </span>
                                ))}
                        </div>

                        {/* Meanings */}
                        <div className="space-y-3 border-t pt-4">
                            {group.meanings?.map((m, mIdx) => (
                                <div key={mIdx} className="space-y-2">
                                    <p className="text-sm leading-relaxed">
                    <span className="font-medium text-muted-foreground mr-2">
                      {mIdx + 1}.
                    </span>
                                        {m.content}
                                    </p>

                                    {/* Examples */}
                                    {m.examples && m.examples.length > 0 && (
                                        <div className="ml-5 space-y-1.5">
                                            {m.examples.map((ex, exIdx) => (
                                                <div key={exIdx} className="text-sm text-muted-foreground">
                                                    <p className="italic">{ex.sentence}</p>
                                                    {ex.translation && (
                                                        <p className="text-xs opacity-80">→ {ex.translation}</p>
                                                    )}
                                                    {ex.note && (
                                                        <p className="text-xs opacity-60">Note: {ex.note}</p>
                                                    )}
                                                </div>
                                            ))}
                                        </div>
                                    )}
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>

            {/* Notes */}
            {data.notes && data.notes.length > 0 && (
                <div className="space-y-2">
                    <h3 className="text-lg font-semibold">Notes</h3>
                    <ul className="space-y-1.5">
                        {data.notes.map((n) => (
                            <li key={n.id} className="text-sm text-muted-foreground">
                                • {n.content}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </>
    );
}