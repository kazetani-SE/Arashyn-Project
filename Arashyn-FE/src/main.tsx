import ReactDOM from "react-dom/client";
import { RouterProvider } from "react-router-dom";
import { QueryClientProvider } from "@tanstack/react-query";

import { router } from "@/app/router";

import "@/styles/globals.css";
import "@/styles/theme.css";
import { enableMocking } from "@/mocks/enable_mocking.ts";
import {queryClient} from "@/lib/query/query_client.ts";
import AuthBootstrap from "@/app/AuthBootstrap.tsx";

enableMocking().then(() => {
    ReactDOM.createRoot(
        document.getElementById("root")!,
    ).render(
        <QueryClientProvider client={queryClient}>
            <AuthBootstrap>
                <RouterProvider router={router} />
            </AuthBootstrap>
        </QueryClientProvider>,
    );
});