import { create } from "zustand";

export interface AuthUser {
    username: string;
    avatar: string | null;
}

interface AuthState {
    accessToken: string | null;
    user: AuthUser | null;
    isInitializing: boolean;

    setAuth: (payload: { accessToken: string; username: string; avatar: string | null }) => void;
    clearAuth: () => void;
    setInitializing: (value: boolean) => void;
}

export const useAuthStore = create<AuthState>((set) => ({
    accessToken: null,
    user: null,
    isInitializing: true,

    setAuth: ({ accessToken, username, avatar }) =>
        set({ accessToken, user: { username, avatar } }),

    clearAuth: () => set({ accessToken: null, user: null }),

    setInitializing: (value) => set({ isInitializing: value }),
}));

// Non-reactive getter
export const getAccessToken = () => useAuthStore.getState().accessToken;