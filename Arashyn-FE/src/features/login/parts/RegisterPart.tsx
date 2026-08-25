import {useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import {ROUTE_PATHS} from "@/app/router/route.ts";
import OAuthButtons from "@/features/login/components/OAuthButtons.tsx";
import Divider from "@/features/login/components/Divider.tsx";
import EmailInput from "@/features/login/components/EmailInput.tsx";
import UsernameInput from "@/features/login/components/UsernameInput.tsx";
import PasswordInput from "@/features/login/components/PasswordInput.tsx";
import ConfirmPasswordInput from "@/features/login/components/ConfirmPasswordInput.tsx";
import {Input} from "@/components/ui/input.tsx";

const OTP_LENGTH = 6;

type Step = "register" | "confirm";

export default function RegisterPart() {
    const [step, setStep] = useState<Step>("register");

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [otp, setOtp] = useState<string[]>(Array(OTP_LENGTH).fill(""));

    const otpRefs = useRef<Array<HTMLInputElement | null>>([]);
    const nav = useNavigate();

    const canGoNext = username.trim() !== "" && email.trim() !== "";
    const canCreateAccount =
        otp.every((d) => d !== "") &&
        password.length > 0 &&
        password === confirmPassword;

    const handleNextStep = () => {
        if (!canGoNext) return;
        // TODO: trigger OTP email send here
        setStep("confirm");
    };

    const handleCreateAccount = () => {
        if (!canCreateAccount) return;
        // TODO: submit { username, email, password, otp: otp.join("") }
    };

    const handleOtpChange = (index: number, rawValue: string) => {
        const value = rawValue.replace(/[^a-zA-Z0-9]/g, "").slice(-1);

        const next = [...otp];
        next[index] = value;
        setOtp(next);

        if (value && index < OTP_LENGTH - 1) {
            otpRefs.current[index + 1]?.focus();
        }
    };

    const handleOtpKeyDown = (index: number, e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Backspace" && !otp[index] && index > 0) {
            otpRefs.current[index - 1]?.focus();
        }
    };

    const handleOtpPaste = (e: React.ClipboardEvent<HTMLInputElement>) => {
        e.preventDefault();
        const pasted = e.clipboardData.getData("text").replace(/[^a-zA-Z0-9]/g, "").slice(0, OTP_LENGTH);
        if (!pasted) return;

        const next = Array(OTP_LENGTH).fill("");
        pasted.split("").forEach((char, i) => {
            next[i] = char;
        });
        setOtp(next);

        const lastIndex = Math.min(pasted.length, OTP_LENGTH) - 1;
        otpRefs.current[lastIndex]?.focus();
    };

    return (
        <div className="flex h-full w-full items-center justify-center">
            <div className="w-[70%]">
                <div className="mb-8">
                    <h1 className="mb-2 text-3xl font-bold">Join Arashyn.</h1>
                    <p className="text-sm text-white/50">
                        Start for free and enjoy learning!
                    </p>
                </div>

                {step === "register" && (
                    <>
                        <OAuthButtons />
                        <Divider />

                        <div className="mb-4 flex flex-col gap-4">
                            <UsernameInput value={username} onChange={setUsername} />
                            <EmailInput value={email} onChange={setEmail} />
                        </div>

                        <button
                            onClick={handleNextStep}
                            disabled={!canGoNext}
                            className="mb-4 w-full rounded-xl bg-indigo-500 py-3.5 font-bold disabled:cursor-not-allowed
                            disabled:opacity-50 mt-4"
                        >
                            Next Step
                        </button>
                    </>
                )}

                {step === "confirm" && (
                    <>
                        <div className="mb-4">
                            <p className="mb-3 text-center text-sm text-white/60">
                                We've sent a {OTP_LENGTH}-digit code to{" "}
                                <span className="font-semibold text-white/80">{email || "your email"}</span>.
                                Please check your inbox (and spam folder) to verify your account.
                            </p>

                            <div className="mb-4 flex justify-center gap-2">
                                {otp.map((digit, index) => (
                                    <Input
                                        key={index}
                                        ref={(el) => (otpRefs.current[index] = el)}
                                        value={digit}
                                        onChange={(e) => handleOtpChange(index, e.target.value)}
                                        onKeyDown={(e) => handleOtpKeyDown(index, e)}
                                        onPaste={handleOtpPaste}
                                        inputMode="text"
                                        maxLength={1}
                                        className="h-12 w-12 text-center text-lg font-semibold"
                                    />
                                ))}
                            </div>
                        </div>

                        <div className="mb-4 flex flex-col gap-4">
                            <PasswordInput value={password} onChange={setPassword} />
                            <ConfirmPasswordInput value={confirmPassword} onChange={setConfirmPassword} />
                        </div>

                        <button
                            onClick={handleCreateAccount}
                            disabled={!canCreateAccount}
                            className="mb-4 w-full rounded-xl bg-indigo-500 py-3.5 font-bold disabled:cursor-not-allowed
                            disabled:opacity-50 mt-4"
                        >
                            Create Account
                        </button>
                    </>
                )}

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