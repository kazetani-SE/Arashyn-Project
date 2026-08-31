import { Mail } from "lucide-react";

interface Props {
    value: string;
    onChange: (value: string) => void;
}

export default function EmailInput({ value, onChange }: Props) {
    return (
        <div>
            <label className="mb-2 block text-xs font-semibold text-white/50">
                Email address
            </label>

            <div className="relative">
                <Mail
                    size={15}
                    className="absolute left-3 top-1/2 -translate-y-1/2 text-white/40"
                />

                <input
                    type="email"
                    value={value}
                    onChange={(e) => onChange(e.target.value)}
                    placeholder="you@example.com"
                    className="w-full rounded-xl border border-white/10 bg-white/[0.03] py-3 pr-4 pl-10
                    outline-none"
                />
            </div>
        </div>
    );
}