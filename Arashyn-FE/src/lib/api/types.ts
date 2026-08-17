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

export type PageParams = {
    page?: number;
    size?: number;
    query?: string;
    filters?: string[];
};

export type PageResponse<T> = {
    items: T[];
    page: number;
    size: number;
    totalPages: number;
    totalElements: number;
    hasNext: boolean;
    hasPrevious: boolean;
};