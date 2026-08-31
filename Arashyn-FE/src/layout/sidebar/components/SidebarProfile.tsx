import { LogIn } from "lucide-react";
import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATHS } from "@/app/router/route.ts";
import {useAuth} from "@/entities/auth/use_auth.ts";

type SidebarProfileProps = {
    collapsed: boolean;
};

export default function SidebarProfile({ collapsed }: SidebarProfileProps) {
    const { isLoggedIn, user } = useAuth();
    const navigate = useNavigate();

    const onLogin = () => {
        navigate(ROUTE_PATHS.LOGIN);
    };

    return (
        <div className="border-t border-sidebar-border p-4">
            {isLoggedIn && user ? (
                <Button
                    variant="ghost"
                    className={`flex h-auto items-center rounded-lg hover:bg-indigo-500/10 ${
                        collapsed ? "mx-auto justify-center p-2" : "w-full justify-start gap-3 p-2"
                    }`}
                >
                    <div className="flex size-10 shrink-0 items-center justify-center rounded-full bg-primary text-primary-foreground">
                        {user.avatar ? (
                            <img src={user.avatar} alt={user.username} className="size-10 rounded-full object-cover" />
                        ) : (
                            user.username.charAt(0).toUpperCase()
                        )}
                    </div>

                    {!collapsed && (
                        <div className="flex-1 text-left">
                            <p className="font-medium text-white">{user.username}</p>
                        </div>
                    )}
                </Button>
            ) : (
                <Button
                    onClick={onLogin}
                    className={`flex items-center gap-2 bg-indigo-500 hover:bg-indigo-500/80 ${
                        collapsed ? "mx-auto w-auto p-2" : "w-full"
                    }`}
                >
                    <LogIn className="size-5 shrink-0" />
                    {!collapsed && <span>Log in</span>}
                </Button>
            )}
        </div>
    );
}