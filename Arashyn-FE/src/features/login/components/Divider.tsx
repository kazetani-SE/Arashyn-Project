export default function Divider() {
    return (
        <div className="mb-6 flex items-center gap-3">
            <div className="h-px flex-1 bg-white/10" />

            <span className="text-xs text-white/40">
                or continue with email
            </span>

            <div className="h-px flex-1 bg-white/10" />
        </div>
    );
}