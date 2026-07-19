import Sidebar from "@/layout/sidebar/Sidebar.tsx";
import {Outlet} from "react-router-dom";
import TopBar from "@/layout/topbar/Topbar.tsx";

export default function MainPage() {
    return (
        <div className="flex h-screen">
            <Sidebar />

            {/* Main Content */}
            <div className="flex flex-1 flex-col overflow-hidden">
                <TopBar />

                <main className="flex-1 overflow-auto">
                    <Outlet />
                </main>
            </div>
        </div>
    );
}