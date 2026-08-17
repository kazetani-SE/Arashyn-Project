import { api } from "@/lib/api/request.ts";
import type { LoginResponse, RegisterResponse } from "@/shared/responses/auth_response.ts";

export interface RegisterVerifyPayload {
    email: string;
    userName: string;
}

export interface VerifyOtpPayload {
    email: string;
    code: string;
}

export interface ResendVerificationPayload {
    email: string;
}

export interface CompleteRegisterPayload {
    email: string;
    password: string;
}

export interface LoginPayload {
    email: string;
    password: string;
}

export interface RefreshPayload {
    accessToken: string;
}

export const authService = {
    /** POST /auth/verify — bắt đầu đăng ký, gửi OTP về email. 409 nếu email đã tồn tại. */
    verifyRegister: (payload: RegisterVerifyPayload) =>
        api.post<void>("/auth/verify", payload),

    /** POST /auth/verify/confirm — xác nhận OTP vừa nhận được */
    confirmVerify: (payload: VerifyOtpPayload) =>
        api.post<void>("/auth/verify/confirm", payload),

    /** POST /auth/verify/resend — gửi lại OTP */
    resendVerification: (payload: ResendVerificationPayload) =>
        api.post<void>("/auth/verify/resend", payload),

    /** POST /auth/complete-register — set password sau khi verify OTP xong, tạo account */
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