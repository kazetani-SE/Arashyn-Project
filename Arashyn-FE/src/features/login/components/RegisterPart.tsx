import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {ROUTE_PATHS} from "@/app/router/route.ts";
import OAuthButtons from "@/features/login/components/supports/OAuthButtons.tsx";
import Divider from "@/features/login/components/supports/Divider.tsx";
import EmailInput from "@/features/login/components/supports/EmailInput.tsx";
import PasswordInput from "@/features/login/components/supports/PasswordInput.tsx";
import UsernameInput from "@/features/login/components/supports/UsernameInput.tsx";
import ConfirmPasswordInput from "@/features/login/components/supports/ConfirmPasswordInput.tsx";

export default function RegisterPart() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const nav = useNavigate();

    return (
        <div className="flex h-full w-full items-center justify-center">
            <div className="w-[70%]">
                <div className="mb-8">
                    <h1 className="mb-2 text-3xl font-bold">Join Arashyn.</h1>
                    <p className="text-sm text-white/50">
                        Start for free and enjoy learning!
                    </p>
                </div>

                <OAuthButtons />
                <Divider />

                <div className="mb-4 flex flex-col gap-4">
                    <UsernameInput value={username} onChange={setUsername} />
                    <EmailInput value={email} onChange={setEmail} />
                    <PasswordInput value={password} onChange={setPassword} />
                    <ConfirmPasswordInput value={confirmPassword} onChange={setConfirmPassword} />
                </div>

                <button className="mb-4 w-full rounded-xl bg-indigo-500 py-3.5 font-bold"
                >
                    Create Account
                </button>

                <p className="text-center text-xs">
                    Already have an account?{" "}
                    <button onClick={() => nav(ROUTE_PATHS.LOGIN)} className="text-indigo-400">
                        Sign In
                    </button>
                </p>

                <p className="mt-6 text-center text-[10px] text-white/40">
                    By continuing, you agree to our Terms of Service and Privacy Policy.
                </p>
            </div>
        </div>
    );
}