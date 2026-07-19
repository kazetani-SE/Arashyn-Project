import type {BreadcrumbProps} from "@/layout/topbar/types/topbar_types.ts";
import {ChevronRight} from "lucide-react";

export default function Breadcrumb({ items }: BreadcrumbProps) {
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
          >
            {item.title}
          </span>

                    {index < items.length - 1 && (
                        <ChevronRight
                            size={14}
                            className="text-slate-500"
                        />
                    )}
                </div>
            ))}
        </nav>
    );
}