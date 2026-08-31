import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATHS } from "@/app/router/route.ts";
import OAuthButtons from "@/features/auth/components/OAuthButtons.tsx";
import Divider from "@/features/auth/components/Divider.tsx";
import EmailInput from "@/features/auth/components/EmailInput.tsx";
import PasswordInput from "@/features/auth/components/PasswordInput.tsx";
import { useLogin } from "@/features/auth/hook/use_login.ts";

export default function LoginPart() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const { login, isPending, error } = useLogin();
    const nav = useNavigate();

    const canSubmit = email.trim() !== "" && password.trim() !== "" && !isPending;

    const errorMessage =
        error?.code === "UNAUTHORIZED"
            ? "Incorrect email or password."
            : error?.code === "NETWORK_ERROR"
                ? "Unable to connect to the server. Please try again."
                : error?.message;

    const handleSubmit = async () => {
        if (!canSubmit) return;

        try {
            await login({ email, password });
            nav(ROUTE_PATHS.DISCOVER);
        } catch {
            //
        }
    };

    return (
        <div className="flex h-full w-full items-center justify-center">
            <div className="w-[70%]">
                <div className="mb-8">
                    <h1 className="mb-2 text-3xl font-bold">Welcome back.</h1>
                    <p className="text-sm text-white/50">Sign in to continue your grammar journey.</p>
                </div>

                <OAuthButtons />
                <Divider />

                <div className="mb-4 flex flex-col gap-4">
                    <EmailInput value={email} onChange={setEmail} />
                    <PasswordInput value={password} onChange={setPassword} />
                </div>

                {errorMessage && (
                    <p className="mb-4 text-center text-xs text-red-400">{errorMessage}</p>
                )}

                <div className="mb-4 flex justify-end">
                    <button
                        className="text-xs text-indigo-400 cursor-pointer"
                        tabIndex={-1}
                    >Forgot password?</button>
                </div>

                <button
                    onClick={handleSubmit}
                    disabled={!canSubmit}
                    className="mb-4 w-full rounded-xl bg-indigo-500 py-3.5 font-bold
                    cursor-pointer hover:bg-indigo-600 disabled:cursor-not-allowed disabled:opacity-50"
                >
                    {isPending ? "Signing in..." : "Sign In"}
                </button>

                <p className="text-center text-xs">
                    Don't have an account?{" "}
                    <button onClick={() => nav(ROUTE_PATHS.REGISTER)}
                            className="text-indigo-400 cursor-pointer">
                        Sign up free
                    </button>
                </p>
            </div>
        </div>
    );
}