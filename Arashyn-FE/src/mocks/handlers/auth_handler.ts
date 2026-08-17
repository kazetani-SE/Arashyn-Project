import { http, HttpResponse } from "msw";
import { mockError, mockSuccess } from "@/mocks/utils.ts";
import type { LoginResponse, RegisterResponse } from "@/shared/responses/auth_response.ts";
import {
    users,
    pendingVerifications,
    makeAccessToken,
    MOCK_OTP,
    OTP_TTL_MS,
} from "@/mocks/constant/auth_data.ts";

const BASE_URL = import.meta.env.VITE_ARASHYN_API_BASE_URL ?? "http://localhost:8080";

export const auth_handler = [
    // POST /auth/verify — Initiate registration and send OTP
    // Actual response: ResponseEntity<Void> → 202 Accepted, NO body
    http.post(`${BASE_URL}/auth/verify`, async ({ request }) => {
        const { email, userName } = (await request.json()) as {
            email: string;
            userName: string;
        };

        if (users.some((u) => u.email.toLowerCase() === email.toLowerCase())) {
            // BE returns 409 without body -> mock also has no body, status only
            return new HttpResponse(null, { status: 409 });
        }

        pendingVerifications.set(email.toLowerCase(), {
            email,
            userName,
            code: MOCK_OTP,
            verified: false,
            expiresAt: Date.now() + OTP_TTL_MS,
        });

        return new HttpResponse(null, { status: 202 });
    }),

    // POST /auth/verify/confirm — Confirm OTP
    // Actual response: 204 No Content, NO body
    http.post(`${BASE_URL}/auth/verify/confirm`, async ({ request }) => {
        const { email, code } = (await request.json()) as { email: string; code: string };
        const pending = pendingVerifications.get(email.toLowerCase());

        if (!pending) {
            return mockError("Verification session not found", 404, {
                code: "NOT_FOUND",
                path: "/auth/verify/confirm",
            });
        }

        if (Date.now() > pending.expiresAt) {
            return mockError("OTP has expired", 400, {
                code: "VALIDATION_ERROR",
                path: "/auth/verify/confirm",
            });
        }

        if (code !== pending.code) {
            return mockError("Invalid OTP code", 400, {
                code: "VALIDATION_ERROR",
                path: "/auth/verify/confirm",
            });
        }

        pending.verified = true;
        return new HttpResponse(null, { status: 204 });
    }),

    // POST /auth/verify/resend — Resend OTP
    // Actual response: 202 Accepted, NO body
    http.post(`${BASE_URL}/auth/verify/resend`, async ({ request }) => {
        const { email } = (await request.json()) as { email: string };
        const pending = pendingVerifications.get(email.toLowerCase());

        if (!pending) {
            return mockError("Verification session not found", 404, {
                code: "NOT_FOUND",
                path: "/auth/verify/resend",
            });
        }

        pending.code = MOCK_OTP;
        pending.expiresAt = Date.now() + OTP_TTL_MS;

        return new HttpResponse(null, { status: 202 });
    }),

    // POST /auth/complete-register — Set password and create actual account
    http.post(`${BASE_URL}/auth/complete-register`, async ({ request }) => {
        const { email, password } = (await request.json()) as { email: string; password: string };
        const pending = pendingVerifications.get(email.toLowerCase());

        if (!pending || !pending.verified) {
            return mockError("Email has not been verified", 400, {
                code: "VALIDATION_ERROR",
                path: "/auth/complete-register",
            });
        }

        users.push({
            email: pending.email,
            userName: pending.userName,
            password,
            avatar: "",
        });
        pendingVerifications.delete(email.toLowerCase());

        const body: RegisterResponse = { message: "Account created successfully" };
        return mockSuccess(body, "Success", { delayMs: "realistic" });
    }),

    // POST /auth/login
    http.post(`${BASE_URL}/auth/login`, async ({ request }) => {
        const { email, password } = (await request.json()) as { email: string; password: string };
        const user = users.find((u) => u.email.toLowerCase() === email.toLowerCase());

        if (!user || user.password !== password) {
            return mockError("Invalid email or password", 401, {
                code: "UNAUTHORIZED",
                path: "/auth/login",
            });
        }

        const body: LoginResponse = {
            username: user.userName,
            avatar: user.avatar,
            accessToken: makeAccessToken(user.email),
        };

        return mockSuccess(body, "Success", { delayMs: "realistic" });
    }),

    // POST /auth/refresh
    http.post(`${BASE_URL}/auth/refresh`, async ({ request }) => {
        const { accessToken } = (await request.json()) as { accessToken: string };

        if (!accessToken) {
            return mockError("Missing access token", 401, {
                code: "UNAUTHORIZED",
                path: "/auth/refresh",
            });
        }

        // Simple mock: extract email from mock token `mock_access_<email>_<ts>`
        const email = accessToken.split("_")[2];
        const user = users.find((u) => u.email === email);

        if (!user) {
            return mockError("Invalid refresh token", 401, {
                code: "UNAUTHORIZED",
                path: "/auth/refresh",
            });
        }

        const body: LoginResponse = {
            username: user.userName,
            avatar: user.avatar,
            accessToken: makeAccessToken(user.email),
        };

        return mockSuccess(body, "Success", { delayMs: "realistic" });
    }),
];