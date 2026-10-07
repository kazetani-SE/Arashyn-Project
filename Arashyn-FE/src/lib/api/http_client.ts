import axios, {
    type AxiosError,
    type AxiosInstance,
    type InternalAxiosRequestConfig,
} from "axios";
import { normalizeError } from "@/lib/api/error_handler.ts";
import { useAuthStore, getAccessToken } from "@/shared/store/auth_store.ts";

const API_BASE_URL = import.meta.env.VITE_ARASHYN_API_BASE_URL || "http://localhost:8080";
const API_TIMEOUT = 15000;

// ================= UNWRAP =================

type Envelope = { data: unknown; message?: string; status?: unknown };

function isEnvelope(body: unknown): body is Envelope {
    return typeof body === "object" && body !== null && "data" in body && "status" in body;
}

function attachUnwrap(instance: AxiosInstance) {
    instance.interceptors.response.use((response) => {
        if (isEnvelope(response.data)) {
            response.data = response.data.data;
        }
        return response;
    });
}

// ================= CLIENTS =================

export const apiClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: API_TIMEOUT,
    headers: { "Content-Type": "application/json" },
    withCredentials: true,
});

const refreshClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: API_TIMEOUT,
    withCredentials: true,
});
attachUnwrap(refreshClient);

// ================= REQUEST =================

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

// ================= RESPONSE =================

const NO_REFRESH_ENDPOINTS = [
    "/auth/login",
    "/auth/register",
    "/auth/refresh",
    "/auth/verify",
    "/auth/forgot-password",
];

function skipRefresh(url?: string): boolean {
    if (!url) return true;
    return NO_REFRESH_ENDPOINTS.some((endpoint) => url.includes(endpoint));
}

let refreshPromise: Promise<string> | null = null;

apiClient.interceptors.response.use(
    (response) => {
        if (isEnvelope(response.data)) {
            response.data = response.data.data;
        }
        return response;
    },
    async (error: AxiosError) => {
        const originalRequest = error.config as
            | (InternalAxiosRequestConfig & { _retry?: boolean })
            | undefined;

        if (
            error.response?.status === 401 &&
            originalRequest &&
            !originalRequest._retry &&
            !skipRefresh(originalRequest.url)
        ) {
            originalRequest._retry = true;

            // Mọi request bị 401 cùng chờ một lần refresh
            if (!refreshPromise) {
                refreshPromise = refreshSession().finally(() => {
                    refreshPromise = null;
                });
            }

            try {
                const newToken = await refreshPromise;
                originalRequest.headers.Authorization = `Bearer ${newToken}`;
                return apiClient(originalRequest);
            } catch (refreshError) {
                useAuthStore.getState().clearAuth();
                window.location.href = "/login";
                return Promise.reject(normalizeError(refreshError));
            }
        }

        return Promise.reject(normalizeError(error));
    },
);

// ================= REFRESH =================

type AuthPayload = { accessToken: string; username: string; avatar: string | null };

export async function refreshSession(): Promise<string> {
    const { data } = await refreshClient.post<AuthPayload>("/auth/refresh");
    useAuthStore.getState().setAuth({
        accessToken: data.accessToken,
        username: data.username,
        avatar: data.avatar,
    });
    return data.accessToken;
}