import LeftPart from "@/features/auth/parts/LeftPart.tsx";
import {Navigate, Outlet} from "react-router-dom";
import {useAuthStore} from "@/shared/store/auth_store.ts";
import {ROUTE_PATHS} from "@/app/router/route.ts";


export default function AuthPage() {
    const isLoggedIn = useAuthStore((s) => !!s.accessToken);

    if (isLoggedIn) {
        return <Navigate to={ROUTE_PATHS.DISCOVER} replace />;
    }

    return (
        <div className="relative min-h-screen overflow-x-hidden">
            <PageBackground/>

            <div className="relative z-10 grid h-screen grid-cols-2">

                <LeftPart/>

                <main>
                    <Outlet/>
                </main>

            </div>
        </div>
    );
}

function PageBackground() {
    return (
        <>
            <div className="absolute left-1/2 top-[-15rem] h-[40rem] w-[40rem] -translate-x-1/2 rounded-full bg-indigo-500/15 blur-3xl" />
            <div className="absolute -left-40 bottom-0 h-[28rem] w-[28rem] rounded-full bg-violet-500/10 blur-3xl" />
            <div className="absolute right-0 top-20 h-[24rem] w-[24rem] rounded-full bg-cyan-500/10 blur-3xl" />
            <div className="absolute inset-0 bg-[radial-gradient(circle_at_top,rgba(99,102,241,0.12),transparent_65%)]" />
        </>
    );
}