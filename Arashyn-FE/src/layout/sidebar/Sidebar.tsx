import { useState } from "react";
import { FOLDERS } from "@/layout/sidebar/constants/folders_constant.ts";
import { MENUS } from "@/layout/sidebar/constants/menuItem_constant.ts";

import SidebarLogo from "@/layout/sidebar/components/SidebarLogo.tsx";
import SidebarNavigation from "@/layout/sidebar/components/SidebarNavigation.tsx";
import SidebarFolders from "@/layout/sidebar/components/SidebarFolders.tsx";
import SidebarProfile from "@/layout/sidebar/components/SidebarProfile.tsx";

export default function Sidebar() {
    const [collapsed, setCollapsed] = useState(false);

    const [expanded, setExpanded] = useState<Record<string, boolean>>(
        () =>
            Object.fromEntries(
                FOLDERS.map((folder) => [folder.id, false])
            )
    );

    const activeMenu = MENUS.find((item) =>
        location.pathname.startsWith(item.path)
    )?.label;

    return (
        <aside
            className={`relative flex h-screen flex-col overflow-hidden border-r border-sidebar-border bg-sidebar transition-all duration-300 ${
                collapsed ? "w-20" : "w-72"
            }`}
        >
            <div className="relative z-10 flex h-full flex-col">

                <SidebarLogo
                    collapsed={collapsed}
                    onToggle={() => setCollapsed((prev) => !prev)}
                />

                <SidebarNavigation
                    collapsed={collapsed}
                    activeMenu={activeMenu}
                />

                {!collapsed ? (
                    <>
                        <div className="mx-4 border-t border-sidebar-border" />

                        <SidebarFolders
                            expanded={expanded}
                            onToggle={(folderId) =>
                                setExpanded((prev) => ({
                                    ...prev,
                                    [folderId]: !prev[folderId],
                                }))
                            }
                        />
                    </>
                ) : (
                    <div className="flex-1" />
                )}

                <SidebarProfile
                    collapsed={collapsed}
                />

            </div>
        </aside>
    );
}