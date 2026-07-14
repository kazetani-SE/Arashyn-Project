import {
    createBrowserRouter,
} from "react-router-dom";
import HomePage from "@/features/home/HomePage.tsx";
import LandingPage from "@/page/LandingPage.tsx";
import {ROUTE_PATHS} from "@/app/router/route.ts";
import LoginPage from "@/features/login/LoginPage.tsx";
import LoginPart from "@/features/login/components/LoginPart.tsx";
import RegisterPart from "@/features/login/components/RegisterPart.tsx";

export const router = createBrowserRouter([
    {
        path: ROUTE_PATHS.DEFAULT,
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
    {
        element:<LoginPage/>,
        children:[
            {
                path: ROUTE_PATHS.LOGIN,
                element: <LoginPart/>,
            },
            {
                path: ROUTE_PATHS.REGISTER,
                element: <RegisterPart/>,
            },
        ]
    },
]);