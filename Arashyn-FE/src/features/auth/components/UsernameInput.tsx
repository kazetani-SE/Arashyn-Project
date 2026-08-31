import { User } from "lucide-react";

interface UsernameInputProps {
    value: string;
    onChange: (value: string) => void;
}

export default function UsernameInput({ value, onChange }: UsernameInputProps) {
    return (
        <div>
            <label className="mb-2 block text-xs font-semibold text-white/50">
                Username
            </label>

            <div className="relative">
                <User
                    size={15}
                    className="absolute left-3 top-1/2 -translate-y-1/2 text-white/40"
                />

                <input
                    type="text"
                    value={value}
                    onChange={(e) => onChange(e.target.value)}
                    placeholder="Enter a username"
                    className="w-full rounded-xl border border-white/10 bg-white/[0.03] py-3 pl-10 pr-4 outline-none transition-colors focus:border-indigo-500"
                />
            </div>
        </div>
    );
}