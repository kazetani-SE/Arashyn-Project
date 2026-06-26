import LandingHeader from "@/layout/home/LandingHeader.tsx";
import {Outlet} from "react-router-dom";
import LandingFooter from "@/layout/home/LandingFooter.tsx";

export default function LandingPage() {
    return (
        <>
            <LandingHeader />

            <main>
                <Outlet />
            </main>

            <LandingFooter />
        </>
    );
}