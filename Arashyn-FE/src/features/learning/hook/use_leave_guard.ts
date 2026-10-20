import { useEffect } from "react";
import { useBlocker } from "react-router-dom";

export function useLeaveGuard(when: boolean) {
    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) =>
            when && currentLocation.pathname !== nextLocation.pathname
    );

    useEffect(() => {
        if (!when) return;
        const handler = (e: BeforeUnloadEvent) => {
            e.preventDefault();
            e.returnValue = "";
        };
        window.addEventListener("beforeunload", handler);
        return () => window.removeEventListener("beforeunload", handler);
    }, [when]);

    return {
        isBlocked: blocker.state === "blocked",
        confirmLeave: () => {
            if (blocker.state === "blocked") blocker.proceed();
        },
        cancelLeave: () => {
            if (blocker.state === "blocked") blocker.reset();
        },
    };
}