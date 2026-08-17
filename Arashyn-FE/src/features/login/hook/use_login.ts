import { useMutation } from "@tanstack/react-query";
import { authService, type LoginPayload } from "@/features/login/service/auth_service.ts";
import { isAppError, normalizeError } from "@/lib/api/error_handler.ts";
import type { AppError } from "@/lib/api/types.ts";
import { useAuthStore } from "@/shared/store/auth_store.ts";

export function useLogin() {
    const setAuth = useAuthStore((s) => s.setAuth);

    const mutation = useMutation({
        mutationFn: (payload: LoginPayload) => authService.login(payload),
        onSuccess: (data) => {
            setAuth({
                accessToken: data.accessToken,
                username: data.username,
                avatar: data.avatar,
            });
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