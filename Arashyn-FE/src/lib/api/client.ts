import axios, { type AxiosError, type InternalAxiosRequestConfig } from "axios";
import { normalizeError } from "@/lib/api/error_handler.ts";

const API_BASE_URL = import.meta.env.VITE_ARASHYN_API_BASE_URL || "http://localhost:8080";
const API_TIMEOUT = 15000;

export const apiClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: API_TIMEOUT,
    headers: {
        "Content-Type": "application/json",
    },
});

// -----------------------------------------------------
// REQUEST INTERCEPTOR
// - Attach access token to headers
// - Optionally attach request-id, locale, etc. if needed
// -----------------------------------------------------
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

// -----------------------------------------------------
// RESPONSE INTERCEPTOR
// - Normalize error into AppError (see error_handler.ts)
// - Handle token refresh on 401 (boilerplate provided, complete with real logic)
// -----------------------------------------------------
let isRefreshing = false;
let pendingQueue: Array<() => void> = [];

apiClient.interceptors.response.use(
    (response) => response,
    async (error: AxiosError) => {
        const originalRequest = error.config as
            | (InternalAxiosRequestConfig & { _retry?: boolean })
            | undefined;

        const status = error.response?.status;

        // --- Token refresh handling template, complete endpoint/logic as needed ---
        if (status === 401 && originalRequest && !originalRequest._retry) {
            originalRequest._retry = true;

            if (isRefreshing) {
                // If refreshing, queue request and retry once token is updated
                return new Promise((resolve) => {
                    pendingQueue.push(() => resolve(apiClient(originalRequest)));
                });
            }

            isRefreshing = true;
            try {
                const newToken = await refreshAccessToken(); // TODO: implement
                setAccessToken(newToken);
                pendingQueue.forEach((cb) => cb());
                pendingQueue = [];
                return apiClient(originalRequest);
            } catch (refreshError) {
                clearAccessToken();
                redirectToLogin(); // TODO: implement (navigate using actual router)
                return Promise.reject(normalizeError(refreshError));
            } finally {
                isRefreshing = false;
            }
        }

        return Promise.reject(normalizeError(error));
    },
);

// -----------------------------------------------------
// Token storage — temporary placeholder, replace with real lib/auth later
// (e.g., store in httpOnly cookies instead of localStorage
// for better security if needed)
// -----------------------------------------------------
export function getAccessToken(): string | null {
    return localStorage.getItem("access_token");
}

export function setAccessToken(token: string): void {
    localStorage.setItem("access_token", token);
}

export function clearAccessToken(): void {
    localStorage.removeItem("access_token");
}

async function refreshAccessToken(): Promise<string> {
    // TODO: Call real refresh-token endpoint, e.g.:
    // const res = await apiClient.post("/auth/refresh");
    // return res.data.data.accessToken;
    throw new Error("refreshAccessToken() has not been implemented yet");
}

function redirectToLogin(): void {
    // TODO: Use actual router (react-router navigate, next/navigation, etc.)
    window.location.href = "/login";
}