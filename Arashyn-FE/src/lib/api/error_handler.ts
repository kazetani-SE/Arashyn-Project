import axios, { type AxiosError } from "axios";
import type { ApiErrorResponse, AppError, AppErrorCode } from "@/lib/api/types.ts";

/**
 * Normalize any error into the application's AppError format.
 */
export function normalizeError(error: unknown): AppError {
    if (axios.isAxiosError<ApiErrorResponse>(error)) {
        return normalizeAxiosError(error);
    }

    if (error instanceof Error) {
        return {
            code: "UNKNOWN_ERROR",
            message: error.message,
            raw: error,
        };
    }

    return {
        code: "UNKNOWN_ERROR",
        message: "An unexpected error occurred.",
        raw: error,
    };
}

const APP_ERROR_CODES: readonly AppErrorCode[] = [
    "NETWORK_ERROR",
    "TIMEOUT",
    "UNAUTHORIZED",
    "FORBIDDEN",
    "NOT_FOUND",
    "VALIDATION_ERROR",
    "SERVER_ERROR",
    "UNKNOWN_ERROR",
];

/**
 * Type guard: is this value already a normalized AppError?
 * Checks `code` against the known AppErrorCode set (not just "has a code
 * and a message"), so it doesn't accidentally match a raw AxiosError
 * (which also has `.code`/`.message`, but with different code values
 * like "ECONNABORTED").
 */
export function isAppError(error: unknown): error is AppError {
    if (!error || typeof error !== "object") return false;
    const candidate = error as Record<string, unknown>;
    return (
        typeof candidate.code === "string" &&
        (APP_ERROR_CODES as readonly string[]).includes(candidate.code) &&
        typeof candidate.message === "string"
    );
}

/**
 * Normalize an Axios error.
 */
function normalizeAxiosError(error: AxiosError<ApiErrorResponse>): AppError {
    if (!error.response) {
        return normalizeConnectionError(error);
    }

    const { status, data } = error.response;

    if (isApiErrorResponse(data)) {
        return {
            code: mapBackendErrorCode(data.code, data.status),
            message: data.message,
            status: data.status,
            raw: error,
            fieldErrors: data.errors ?? undefined,
        };
    }

    return {
        code: mapHttpStatus(status),
        message: error.message || "An unexpected server error occurred.",
        status,
        raw: error,
    };
}

/**
 * Handles errors where Axios did not receive an HTTP response.
 */
function normalizeConnectionError(error: AxiosError): AppError {
    if (error.code === "ECONNABORTED" || error.code === "ETIMEDOUT") {
        return {
            code: "TIMEOUT",
            message: "Request timed out.",
            raw: error,
        };
    }

    return {
        code: "NETWORK_ERROR",
        message: "Unable to connect to the server.",
        raw: error,
    };
}

/**
 * Maps backend business error codes into frontend-level error categories.
 */
function mapBackendErrorCode(backendCode: string, status: number): AppErrorCode {
    switch (backendCode) {
        case "UNAUTHORIZED": return "UNAUTHORIZED";
        case "FORBIDDEN": return "FORBIDDEN";
        case "VALIDATION_ERROR": return "VALIDATION_ERROR";
        case "NOT_FOUND":
        case "GRAMMAR_NOT_FOUND": return "NOT_FOUND";
        case "INTERNAL_SERVER_ERROR": return "SERVER_ERROR";
        default: return mapHttpStatus(status);
    }
}

/**
 * Fallback mapping based on HTTP status.
 */
function mapHttpStatus(status: number): AppErrorCode {
    switch (status) {
        case 400:
        case 422: return "VALIDATION_ERROR";
        case 401: return "UNAUTHORIZED";
        case 403: return "FORBIDDEN";
        case 404: return "NOT_FOUND";
        case 500:
        case 502:
        case 503:
        case 504: return "SERVER_ERROR";
        default: return "UNKNOWN_ERROR";
    }
}

/**
 * Runtime validation for the backend error response.
 */
function isApiErrorResponse(value: unknown): value is ApiErrorResponse {
    if (!value || typeof value !== "object") return false;
    const data = value as Record<string, unknown>;
    return (
        typeof data.status === "number" &&
        typeof data.code === "string" &&
        typeof data.message === "string"
    );
}