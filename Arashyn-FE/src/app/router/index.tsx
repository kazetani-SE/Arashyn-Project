import {
    createBrowserRouter,
} from "react-router-dom";
import HomePage from "@/features/home/HomePage.tsx";
import LandingPage from "@/page/LandingPage.tsx";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <LandingPage />,
        children: [
            {
                index: true,
                element: <HomePage />,
            },
            {
                path: "home",
                element: <HomePage />,
            },
        ],
    },
]);