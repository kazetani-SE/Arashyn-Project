import { Eye, EyeOff, LockKeyhole } from "lucide-react";
import { useState } from "react";

interface ConfirmPasswordInputProps {
    value: string;
    onChange: (value: string) => void;
}

export default function ConfirmPasswordInput({ value, onChange }: ConfirmPasswordInputProps) {
    const [show, setShow] = useState(false);

    return (
        <div>
            <label className="mb-2 block text-xs font-semibold text-white/50">
                Confirm password
            </label>

            <div className="relative">
                <LockKeyhole
                    size={15}
                    className="absolute left-3 top-1/2 -translate-y-1/2 text-white/40"
                />

                <input
                    type={show ? "text" : "password"}
                    value={value}
                    onChange={(e) => onChange(e.target.value)}
                    placeholder="Re-enter your password"
                    className="w-full rounded-xl border border-white/10 bg-white/[0.03] py-3 pl-10 pr-10 outline-none transition-colors focus:border-indigo-500"
                />

                <button
                    type="button"
                    onClick={() => setShow((prev) => !prev)}
                    className="absolute right-3 top-1/2 -translate-y-1/2"
                    tabIndex={-1}
                >
                    {show ? (
                        <EyeOff size={15} className="text-white/40" />
                    ) : (
                        <Eye size={15} className="text-white/40" />
                    )}
                </button>
            </div>
        </div>
    );
}