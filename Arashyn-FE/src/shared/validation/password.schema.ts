import { z } from "zod";
import { PASSWORD_SPECIAL_REGEX } from "./regex";

export const passwordSchema = z
    .string()
    .min(8, "Password must contain at least 8 characters.")
    .regex(/[A-Z]/, "Password must contain an uppercase letter.")
    .regex(/[a-z]/, "Password must contain a lowercase letter.")
    .regex(/[0-9]/, "Password must contain a number.")
    .regex(
        PASSWORD_SPECIAL_REGEX,
        "Password must contain a special character."
    );