import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {ROUTE_PATHS} from "@/app/router/route.ts";
import OAuthButtons from "@/features/login/components/supports/OAuthButtons.tsx";
import Divider from "@/features/login/components/supports/Divider.tsx";
import EmailInput from "@/features/login/components/supports/EmailInput.tsx";
import PasswordInput from "@/features/login/components/supports/PasswordInput.tsx";

export default function LoginPart() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const nav = useNavigate();

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

                <div className="mb-4 flex justify-end">
                    <button
                        className="text-xs text-indigo-400 cursor-pointer"
                        tabIndex={-1}
                    >Forgot password?</button>
                </div>

                <button className="mb-4 w-full rounded-xl bg-indigo-500 py-3.5 font-bold
                cursor-pointer hover:bg-indigo-600">
                    Sign In
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