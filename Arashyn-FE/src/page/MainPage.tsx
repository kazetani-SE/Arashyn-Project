import Sidebar from "@/layout/sidebar/Sidebar.tsx";
import { Outlet } from "react-router-dom";
import TopBar from "@/layout/topbar/Topbar.tsx";
import { BreadcrumbProvider } from "@/layout/topbar/contexts/BreadcrumbContext.tsx";

export default function MainPage() {
    return (
        <BreadcrumbProvider>
            <div className="flex h-screen">
                <Sidebar />

                {/* Main Content */}
                <div className="flex flex-1 flex-col overflow-hidden">
                    <TopBar />

                    <main className="flex-1 overflow-auto scrollbar-dark">
                        <Outlet />
                    </main>
                </div>
            </div>
        </BreadcrumbProvider>
    );
}