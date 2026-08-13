export const AUTH_ROLES = {
    GUEST: "guest",
    USER: "USER",
    CONTRIBUTOR: "CONTRIBUTOR",
    ADMIN: "ADMIN",
} as const;

export type AuthRole = (typeof AUTH_ROLES)[keyof typeof AUTH_ROLES];

export const AUTHENTICATED_ROLES: AuthRole[] = Object.values(AUTH_ROLES);

const VALID_ROLES = new Set<string>(AUTHENTICATED_ROLES);

export function normalizeRole(raw: unknown): AuthRole | null {
    if (!raw) return null;

    const normalized = String(raw).replace(/^ROLE_/, "").trim().toUpperCase();

    return VALID_ROLES.has(normalized) ? (normalized as AuthRole) : null;
}