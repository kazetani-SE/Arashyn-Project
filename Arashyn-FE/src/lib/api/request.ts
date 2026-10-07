import type { AxiosRequestConfig, AxiosResponse } from "axios";
import { apiClient } from "@/lib/api/http_client.ts";

type RequestConfig = Omit<AxiosRequestConfig, "params"> & {
    params?: Record<string, string | number | boolean | undefined>;
};

/**
 * Interceptor của apiClient đã bóc { data, message, status },
 * nên response.data đã là body thật.
 * Endpoint 202/204 không có body trả "" hoặc undefined -> chuẩn hóa thành undefined.
 */
function unwrap<T>(response: AxiosResponse<T | "" | undefined | null>): T {
    const body = response.data;

    if (body === undefined || body === "" || body === null) {
        return undefined as T;
    }

    return body as T;
}

export const api = {
    get: async <T>(url: string, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.get<T>(url, config)),

    post: async <T>(url: string, body?: unknown, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.post<T>(url, body, config)),

    put: async <T>(url: string, body?: unknown, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.put<T>(url, body, config)),

    patch: async <T>(url: string, body?: unknown, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.patch<T>(url, body, config)),

    delete: async <T>(url: string, config?: RequestConfig): Promise<T> =>
        unwrap(await apiClient.delete<T>(url, config)),
};