import { useAuthStore } from "@/shared/store/auth_store.ts";

export function useAuth() {
    const accessToken = useAuthStore((s) => s.accessToken);
    const user = useAuthStore((s) => s.user);
    const isInitializing = useAuthStore((s) => s.isInitializing);
    const clearAuth = useAuthStore((s) => s.clearAuth);

    return {
        isLoggedIn: !!accessToken,
        user,
        isInitializing,
        logout: async () => {
            await import("@/features/login/service/auth_service.ts")
                .then(({ authService }) => authService.logout());
            clearAuth();
        },
    };
}