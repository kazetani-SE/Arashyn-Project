type SidebarProfileProps = {
    collapsed: boolean;
};

export default function SidebarProfile({
                                           collapsed,
                                       }: SidebarProfileProps) {
    return (
        <div className="border-t border-sidebar-border p-4">
            <button
                className={`flex items-center rounded-lg transition hover:bg-indigo-500/10 ${
                    collapsed
                        ? "mx-auto justify-center p-2"
                        : "w-full gap-3 p-2"
                }`}
            >
                <div className="flex size-10 shrink-0 items-center justify-center rounded-full bg-primary text-primary-foreground">
                    P
                </div>

                {!collapsed && (
                    <div className="flex-1 text-left">
                        <p className="font-medium text-white">Phong</p>
                        <p className="text-sm text-slate-400">
                            42-day streak 🔥
                        </p>
                    </div>
                )}
            </button>
        </div>
    );
}