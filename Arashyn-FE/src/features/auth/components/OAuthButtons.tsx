import {FaApple, FaGithub, FaGoogle} from "react-icons/fa";

export default function OAuthButtons() {
    return (
        <div className="mb-6 flex flex-col gap-3">
            <button className={oauthButtons}>
                <div className="flex items-center justify-center gap-3">
                    <FaGithub />
                    Continue with GitHub
                </div>
            </button>

            <button className={oauthButtons}>
                <div className="flex items-center justify-center gap-3">
                    <FaGoogle />
                    Continue with Google
                </div>
            </button>

            <button className={oauthButtons}>
                <div className="flex items-center justify-center gap-3">
                    <FaApple />
                    Continue with Apple
                </div>
            </button>
        </div>
    );
}

const oauthButtons = "rounded-xl border border-white/10 bg-white/[0.03] py-3 font-medium cursor-pointer " +
    "transition-all duration-200 ease-out hover:-translate-y-1 hover:border-white/20 hover:bg-white/[0.06] " +
    "hover:shadow-lg active:translate-y-0 active:scale-[0.98]";