import { GraduationCap, PanelLeft } from "lucide-react";

type SidebarLogoProps = {
    collapsed: boolean;
    onToggle: () => void;
};

export default function SidebarLogo({
                                        collapsed,
                                        onToggle,
                                    }: SidebarLogoProps) {
    return (
        <div
            className={`flex items-center border-b border-sidebar-border py-5 ${
                collapsed ? "flex-col gap-2 px-2" : "px-5"
            }`}
        >
            <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-primary text-primary-foreground">
                <GraduationCap className="size-5" />
            </div>

            {!collapsed && (
                <div className="ml-3 flex-1">
                    <h3 className="font-semibold text-white">Arashyn</h3>
                    <p className="text-sm text-slate-400">
                        Language Platform
                    </p>
                </div>
            )}

            <button
                onClick={onToggle}
                className={`rounded-lg p-2 text-slate-400 transition hover:bg-indigo-500/10 hover:text-white ${
                    collapsed ? "" : "ml-auto"
                }`}
            >
                <PanelLeft
                    className={`size-4 transition-transform ${
                        collapsed ? "rotate-180" : ""
                    }`}
                />
            </button>
        </div>
    );
}