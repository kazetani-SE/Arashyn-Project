import { useNavigate } from "react-router-dom";
import { MENUS } from "@/layout/sidebar/constants/menuItem_constant.ts";

type SidebarNavigationProps = {
    collapsed: boolean;
    activeMenu?: string;
};

export default function SidebarNavigation({
                                              collapsed,
                                              activeMenu,
                                          }: SidebarNavigationProps) {
    const navigate = useNavigate();

    return (
        <nav className={`space-y-1 ${collapsed ? "p-2" : "p-4"}`}>
            {MENUS.map((item) => {
                const Icon = item.icon;
                const isActive = activeMenu === item.label;

                return (
                    <button
                        key={item.label}
                        onClick={() => navigate(item.path)}
                        title={collapsed ? item.label : undefined}
                        className={`flex items-center rounded-full border transition ${
                            collapsed
                                ? "mx-auto h-11 w-11 justify-center"
                                : "w-full justify-start gap-3 px-4 py-2.5"
                        } ${
                            isActive
                                ? "border-indigo-500/30 bg-indigo-500/15 text-indigo-300"
                                : "border-transparent text-slate-300 hover:border-indigo-500/20 hover:bg-indigo-500/10 hover:text-indigo-200"
                        }`}
                    >
                        <Icon className="size-4 shrink-0" />

                        {!collapsed && (
                            <span className="text-sm font-medium">
                                {item.label}
                            </span>
                        )}
                    </button>
                );
            })}
        </nav>
    );
}