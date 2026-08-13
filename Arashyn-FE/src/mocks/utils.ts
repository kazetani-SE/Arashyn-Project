import { HttpResponse, delay } from "msw";
import type {
    ApiErrorResponse,
    ApiResponse,
} from "@/lib/api/types.ts";

interface MockOptions {
    delayMs?: number | "realistic";
    status?: number;
}

interface MockErrorOptions extends MockOptions {
    code?: string;
    path?: string;
    errors?: Record<string, string> | null;
}

export async function mockSuccess<T>(
    data: T,
    message = "Success",
    options: MockOptions = {},
) {
    await applyDelay(options.delayMs);

    const status = options.status ?? 200;

    const body: ApiResponse<T> = {
        status,
        message,
        data,
    };

    return HttpResponse.json(body, {
        status,
    });
}

export async function mockError(
    message = "Unexpected server error.",
    status = 500,
    options: MockErrorOptions = {},
) {
    await applyDelay(options.delayMs);

    const body: ApiErrorResponse = {
        timestamp: new Date().toISOString(),
        status,
        code: options.code ?? getDefaultErrorCode(status),
        message,
        path: options.path ?? "",
        errors: options.errors ?? null,
    };

    return HttpResponse.json(body, {
        status,
    });
}

function getDefaultErrorCode(status: number): string {
    switch (status) {
        case 400:
            return "BAD_REQUEST";
        case 401:
            return "UNAUTHORIZED";
        case 403:
            return "FORBIDDEN";
        case 404:
            return "NOT_FOUND";
        case 409:
            return "CONFLICT";
        case 422:
            return "VALIDATION_ERROR";
        case 500:
            return "INTERNAL_SERVER_ERROR";
        default:
            return "UNKNOWN_ERROR";
    }
}

async function applyDelay(delayMs?: number | "realistic") {
    if (delayMs === "realistic") {
        await delay(300 + Math.random() * 500);
    } else if (typeof delayMs === "number") {
        await delay(delayMs);
    }
}

export function shouldFail(probability = 0.2): boolean {
    return Math.random() < probability;
}