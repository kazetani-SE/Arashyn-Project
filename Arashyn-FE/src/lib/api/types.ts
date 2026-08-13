export type ApiResponse<T> = {
    status: number;
    message: string;
    data: T;
};

export type ApiErrorResponse = {
    timestamp: string;
    status: number;
    code: string;
    message: string;
    path: string;
    errors: Record<string, string> | null;
};

export type AppError = {
    code: AppErrorCode;
    message: string;
    status?: number;
    raw?: unknown;
    fieldErrors?: Record<string, string>;
};

export type AppErrorCode =
    | "NETWORK_ERROR"
    | "TIMEOUT"
    | "UNAUTHORIZED"
    | "FORBIDDEN"
    | "NOT_FOUND"
    | "VALIDATION_ERROR"
    | "SERVER_ERROR"
    | "UNKNOWN_ERROR";

export type ApiCall<TRequest, TResponse> = (
    payload: TRequest
) => Promise<ApiResponse<TResponse>>;