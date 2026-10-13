import { api } from "@/lib/api/request.ts";
import type {
    LoginResponse,
    RegisterResponse,
    RegisterVerifyPayload,
    VerifyOtpPayload,
    ResendVerificationPayload,
    CompleteRegisterPayload,
    LoginPayload,
} from "./auth_types.ts";

export const authService = {
    verifyRegister: (payload: RegisterVerifyPayload) =>
        api.post<void>("/auth/verify", payload),

    /** POST /auth/verify/confirm */
    confirmVerify: (payload: VerifyOtpPayload) =>
        api.post<void>("/auth/verify/confirm", payload),

    /** POST /auth/verify/resend */
    resendVerification: (payload: ResendVerificationPayload) =>
        api.post<void>("/auth/verify/resend", payload),

    /** POST /auth/complete-register */
    completeRegister: (payload: CompleteRegisterPayload) =>
        api.post<RegisterResponse>("/auth/complete-register", payload),

    /** POST /auth/login */
    login: (payload: LoginPayload) =>
        api.post<LoginResponse>("/auth/login", payload),

    /** POST /auth/refresh */
    refresh: () =>
        api.post<LoginResponse>("/auth/refresh"),

    /** POST /auth/logout */
    logout: () =>
        api.post<void>("/auth/logout"),
};