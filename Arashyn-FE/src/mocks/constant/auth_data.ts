export interface PendingVerification {
    email: string;
    userName: string;
    code: string;
    verified: boolean;
    expiresAt: number; // epoch ms
}

export interface MockUser {
    email: string;
    userName: string;
    password: string;
    avatar: string;
}

// Fixed OTP for quick testing without checking real email in mock environment
export const MOCK_OTP = "123456";
export const OTP_TTL_MS = 5 * 60 * 1000; // 5 minutes

// Simulated existing users DB — for testing "email already registered" case (409)
export const users: MockUser[] = [
    {
        email: "demo@arashyn.io",
        userName: "demo_user",
        password: "password123",
        avatar: "",
    },
];

// Pending OTP verification session, key = email (matches BE tracking strategy by email)
export const pendingVerifications = new Map<string, PendingVerification>();

export function makeAccessToken(email: string) {
    return `mock_access_${email}_${Date.now()}`;
}