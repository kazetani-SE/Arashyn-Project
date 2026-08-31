import { useQuery } from "@tanstack/react-query";
import { grammarService } from "@/entities/grammar/grammar_service.ts";
import { isAppError, normalizeError } from "@/lib/api/error_handler.ts";
import type { AppError } from "@/lib/api/types.ts";

export function useGrammarDetail(grammarId: string | undefined) {
    const query = useQuery({
        queryKey: ["grammar-detail", grammarId],
        queryFn: () => grammarService.getDetail(grammarId as string),
        enabled: !!grammarId,
        retry: (failureCount, error) => {
            const normalized = normalizeError(error);
            if (normalized.code === "NOT_FOUND") return false;
            return failureCount < 3;
        },
    });

    const error: AppError | null = query.error
        ? isAppError(query.error)
            ? query.error
            : normalizeError(query.error)
        : null;

    return {
        data: query.data,
        isLoading: query.isLoading,
        error,
    };
}