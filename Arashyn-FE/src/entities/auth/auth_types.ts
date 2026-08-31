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

export interface LoginResponse {
    username: string;
    avatar: string;
    accessToken: string;
}

export interface RegisterResponse {
    message: string;
}