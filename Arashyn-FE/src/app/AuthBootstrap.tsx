import { useEffect } from "react";
import { useAuthStore } from "@/shared/store/auth_store.ts";
import {authService} from "@/entities/auth/auth_service.ts";

export default function AuthBootstrap({ children }: { children: React.ReactNode }) {
    const setAuth = useAuthStore((s) => s.setAuth);
    const setInitializing = useAuthStore((s) => s.setInitializing);
    const isInitializing = useAuthStore((s) => s.isInitializing);

    useEffect(() => {
        authService
            .refresh()
            .then((data) => {
                setAuth({
                    accessToken: data.accessToken,
                    username: data.username,
                    avatar: data.avatar,
                });
            })
            .catch(() => {
                // chưa login hoặc session hết hạn — bỏ qua, coi như guest
            })
            .finally(() => setInitializing(false));
    }, []);

    if (isInitializing) {
        return <div className="flex h-screen items-center justify-center">Loading...</div>;
    }

    return <>{children}</>;
}