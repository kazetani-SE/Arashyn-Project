import { useMutation } from "@tanstack/react-query";
import { authService, type LoginPayload } from "@/features/login/service/auth_service.ts";
import { setAccessToken } from "@/lib/api/client.ts";
import { isAppError, normalizeError } from "@/lib/api/error_handler.ts";
import type {AppError} from "@/lib/api/types.ts";

export function useLogin() {
    const mutation = useMutation({
        mutationFn: (payload: LoginPayload) => authService.login(payload),
        onSuccess: (data) => {
            setAccessToken(data.accessToken);
        },
    });

    const error: AppError | null = mutation.error
        ? isAppError(mutation.error)
            ? mutation.error
            : normalizeError(mutation.error)
        : null;

    return {
        login: mutation.mutateAsync,
        data: mutation.data,
        isPending: mutation.isPending,
        error,
        reset: mutation.reset,
    };
}