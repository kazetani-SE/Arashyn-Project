import {ChevronRight} from "lucide-react";
import {useNavigate} from "react-router-dom";
import {useBreadcrumbContext} from "@/layout/topbar/contexts/BreadcrumbContext.tsx";

export default function Breadcrumb() {
    const navigate = useNavigate();
    const { items } = useBreadcrumbContext();

    const handleOnclick = (href: string) => {
        navigate(href);
    };

    return (
        <nav className="flex items-center gap-1 text-sm whitespace-nowrap">
            {items.map((item, index) => (
                <div key={item.href} className="flex items-center gap-1">
                    <span
                        className={`transition-colors duration-200 cursor-pointer ${
                            index === items.length - 1
                                ? "font-medium text-white"
                                : "text-slate-400"
                        }`}
                        onClick={() => handleOnclick(item.href)}
                    >
                        {item.title}
                    </span>
                    {index < items.length - 1 && (
                        <ChevronRight size={14} className="text-slate-500" />
                    )}
                </div>
            ))}
        </nav>
    );
}