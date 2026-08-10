import {
    createBrowserRouter,
} from "react-router-dom";
import HomePage from "@/features/home/HomePage.tsx";
import LandingPage from "@/page/LandingPage.tsx";
import {ROUTE_PATHS} from "@/app/router/route.ts";
import LoginPage from "@/features/login/LoginPage.tsx";
import LoginPart from "@/features/login/components/LoginPart.tsx";
import RegisterPart from "@/features/login/components/RegisterPart.tsx";
import DiscoverPage from "@/features/discover/DiscoverPage.tsx";
import MainPage from "@/page/MainPage.tsx";
import SettingPage from "@/features/setting/SettingPage.tsx";
import DashboardPage from "@/features/dashboard/DashboardPage.tsx";
import CommunityPage from "@/features/community/CommunityPage.tsx";
import DetailPage from "@/features/detail/DetailPage.tsx";
import ItemListPage from "@/features/popular/ItemListPage.tsx";

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
    {
        element:<MainPage/>,
        children:[
            {
                path: ROUTE_PATHS.DISCOVER,
                element: <DiscoverPage/>,
            },
            {
                path: ROUTE_PATHS.SETTINGS,
                element: <SettingPage/>,
            },
            {
                path: ROUTE_PATHS.DASHBOARD,
                element: <DashboardPage/>,
            },
            {
                path: ROUTE_PATHS.COMMUNITY,
                element: <CommunityPage/>,
            },
            {
                path: ROUTE_PATHS.ITEM_LIST,
                element: <ItemListPage/>,
            },
            {
                path: ROUTE_PATHS.SEARCH,
                element: <ItemListPage/>,
            },
            {
                path: ROUTE_PATHS.DETAIL,
                element: <DetailPage/>,
            },
        ]
    },
]);