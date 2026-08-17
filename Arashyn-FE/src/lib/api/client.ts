import axios, { type AxiosError, type InternalAxiosRequestConfig } from "axios";
import { normalizeError } from "@/lib/api/error_handler.ts";
import { useAuthStore } from "@/shared/store/auth_store.ts";

const API_BASE_URL = import.meta.env.VITE_ARASHYN_API_BASE_URL || "http://localhost:8080";
const API_TIMEOUT = 15000;

export const apiClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: API_TIMEOUT,
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
});

apiClient.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const token = getAccessToken();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(normalizeError(error)),
);

let isRefreshing = false;
let pendingQueue: Array<() => void> = [];

const AUTH_ENDPOINTS = ["/auth/login", "/auth/refresh", "/auth/verify"];

function isAuthEndpoint(url?: string): boolean {
    if (!url) return false;
    return AUTH_ENDPOINTS.some((endpoint) => url.includes(endpoint));
}

apiClient.interceptors.response.use(
    (response) => response,
    async (error: AxiosError) => {
        const originalRequest = error.config as
            | (InternalAxiosRequestConfig & { _retry?: boolean })
            | undefined;

        const status = error.response?.status;

        if (
            status === 401 &&
            originalRequest &&
            !originalRequest._retry &&
            !isAuthEndpoint(originalRequest.url)
        ) {
            originalRequest._retry = true;

            if (isRefreshing) {
                return new Promise((resolve) => {
                    pendingQueue.push(() => resolve(apiClient(originalRequest)));
                });
            }

            isRefreshing = true;
            try {
                const newToken = await refreshAccessToken();
                setAccessToken(newToken);
                pendingQueue.forEach((cb) => cb());
                pendingQueue = [];
                return apiClient(originalRequest);
            } catch (refreshError) {
                clearAccessToken();
                pendingQueue.forEach((cb) => cb());
                pendingQueue = [];
                redirectToLogin();
                return Promise.reject(normalizeError(refreshError));
            } finally {
                isRefreshing = false;
            }
        }

        return Promise.reject(normalizeError(error));
    },
);

// -----------------------------------------------------
// Token storage — backed by useAuthStore (in-memory), not localStorage
// -----------------------------------------------------
export function getAccessToken(): string | null {
    return useAuthStore.getState().accessToken;
}

export function setAccessToken(token: string): void {
    useAuthStore.setState({ accessToken: token });
}

export function clearAccessToken(): void {
    useAuthStore.getState().clearAuth();
}

async function refreshAccessToken(): Promise<string> {
    const res = await apiClient.post("/auth/refresh");
    const data = res.data.data;
    useAuthStore.getState().setAuth({
        accessToken: data.accessToken,
        username: data.username,
        avatar: data.avatar,
    });
    return data.accessToken;
}

function redirectToLogin(): void {
    window.location.href = "/login";
}