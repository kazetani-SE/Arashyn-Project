import { z } from "zod";
import { passwordSchema } from "./password.schema";

export const registerSchema = z
    .object({
        username: z
            .string()
            .min(2, "Username must contain at least 2 characters.")
            .max(30, "Username must not exceed 30 characters."),

        email: z.email("Invalid email address."),

        password: passwordSchema,

        confirmPassword: z.string(),
    })
    .refine(
        data => data.password === data.confirmPassword,
        {
            path: ["confirmPassword"],
            message: "Passwords do not match.",
        }
    );

export type RegisterFormData =
    z.infer<typeof registerSchema>;