// =====================================================
// Shared React Query configuration for the entire app.
// Note: retry/staleTime behavior should be set CAUTIOUSLY,
// because mock data returns almost instantly (masking potential issues),
// whereas a real API has actual latency + error rates => leaving defaults
// can easily cause weird UX (flickering loading states, rapid retries...).
// =====================================================

import { QueryClient } from "@tanstack/react-query";
import { isAppError, normalizeError } from "@/lib/api/error_handler.ts";
import type { AppErrorCode } from "@/lib/api/types.ts";

const NO_RETRY_CODES: readonly AppErrorCode[] = [
    "UNAUTHORIZED",
    "FORBIDDEN",
    "NOT_FOUND",
    "VALIDATION_ERROR",
];

export const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            // Do not automatically refetch when refocusing the tab (prevents spamming real API)
            refetchOnWindowFocus: false,

            // Data is considered "stale" after 30s, preventing redundant API calls
            staleTime: 30 * 1000,

            // Do not retry on 401/403/404/422 errors (retrying is pointless),
            // only retry network/server errors (5xx) a maximum of 1 time
            retry: (failureCount, error) => {
                // By the time an error reaches here it should already be an
                // AppError (the axios response interceptor normalizes it),
                // but fall back to normalizeError() for anything that
                // bypassed that path (e.g. a thrown error inside queryFn).
                const appError = isAppError(error) ? error : normalizeError(error);

                if (NO_RETRY_CODES.includes(appError.code)) return false;
                return failureCount < 1;
            },
        },
        mutations: {
            // Mutations (POST/PUT/DELETE) do NOT retry automatically by default,
            // to prevent duplicate requests (e.g., placing an order twice)
            retry: false,
        },
    },
});