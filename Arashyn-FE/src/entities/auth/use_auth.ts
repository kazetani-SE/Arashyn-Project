import { useCallback } from "react";
import { useAuthStore } from "@/shared/store/auth_store.ts";
import {authService} from "@/entities/auth/auth_service.ts";

export function useAuth() {
    const accessToken = useAuthStore((s) => s.accessToken);
    const user = useAuthStore((s) => s.user);
    const isInitializing = useAuthStore((s) => s.isInitializing);
    const clearAuth = useAuthStore((s) => s.clearAuth);

    const logout = useCallback(async () => {
        await authService.logout();
        clearAuth();
    }, [clearAuth]);

    return {
        isLoggedIn: !!accessToken,
        user,
        isInitializing,
        logout,
    };
}