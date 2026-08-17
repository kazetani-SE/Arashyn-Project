// lib/api/request.ts
import type { AxiosRequestConfig, AxiosResponse } from "axios";
import { apiClient } from "@/lib/api/client.ts";
import type { ApiResponse } from "@/lib/api/types.ts";

type RequestConfig = Omit<AxiosRequestConfig, "params"> & {
    params?: Record<string, string | number | boolean | undefined>;
};

/**
 * Unwrap response.data.data.
 * Some endpoints return 202/204 WITHOUT a body (ResponseEntity<Void>) —
 * in those cases, response.data will be "" or undefined, not ApiResponse<T>.
 * For these scenarios, return undefined instead of trying to read .data, which causes errors.
 */
function unwrap<T>(response: AxiosResponse<ApiResponse<T> | "" | undefined>): T {
    const body = response.data;

    if (body === undefined || body === "" || body === null) {
        return undefined as T;
    }

    return (body as ApiResponse<T>).data;
}

export const api = {
    get: async <T>(url: string, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.get<ApiResponse<T>>(url, config)),

    post: async <T>(url: string, body?: unknown, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.post<ApiResponse<T>>(url, body, config)),

    put: async <T>(url: string, body?: unknown, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.put<ApiResponse<T>>(url, body, config)),

    patch: async <T>(url: string, body?: unknown, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.patch<ApiResponse<T>>(url, body, config)),

    delete: async <T>(url: string, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.delete<ApiResponse<T>>(url, config)),
};