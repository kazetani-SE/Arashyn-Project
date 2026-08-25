import { useState } from "react";
import { Eye, EyeOff, Lock } from "lucide-react";

interface Props {
    value: string;
    onChange: (value: string) => void;
}

export default function PasswordInput({ value, onChange }: Props) {
    const [show, setShow] = useState(false);

    return (
        <div>
            <label className="mb-2 block text-xs font-semibold text-white/50">
                Password
            </label>

            <div className="relative">
                <Lock
                    size={15}
                    className="absolute left-3 top-1/2 -translate-y-1/2 text-white/40"
                />

                <input
                    type={show ? "text" : "password"}
                    value={value}
                    onChange={(e) => onChange(e.target.value)}
                    className="w-full rounded-xl border border-white/10 bg-white/[0.03] py-3
                    pr-10 pl-10 outline-none"
                />

                <button
                    type="button"
                    onClick={() => setShow(!show)}
                    className="absolute right-3 top-1/2 -translate-y-1/2 cursor-pointer"
                    tabIndex={-1}
                >
                    {show ? <EyeOff size={15} /> : <Eye size={15} />}
                </button>
            </div>
        </div>
    );
}