import { ChevronRight } from "lucide-react";
import { useNavigate } from "react-router-dom";
import {breadcrumbKey, useBreadcrumbStore} from "@/shared/store/breadcrumb_store.ts";

export default function Breadcrumb() {
    const navigate = useNavigate();
    const items = useBreadcrumbStore((s) => s.items);

    return (
        <nav className="flex items-center gap-1 text-sm whitespace-nowrap">
            {items.map((item, index) => {
                const isLast = index === items.length - 1;

                return (
                    <div key={breadcrumbKey(item)} className="flex items-center gap-1">
                        <span
                            className={`max-w-[180px] cursor-pointer truncate transition-colors duration-200 ${
                                isLast ? "font-medium text-white" : "text-slate-400 hover:text-slate-200"
                            }`}
                            onClick={() => navigate(item.href)}
                        >
                            {item.title}
                        </span>
                        {!isLast && <ChevronRight size={14} className="text-slate-500" />}
                    </div>
                );
            })}
        </nav>
    );
}