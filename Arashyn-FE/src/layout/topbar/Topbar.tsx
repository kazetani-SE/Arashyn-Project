import LanguageSwitcher from "@/layout/topbar/components/LanguageSwitcher.tsx";
import Breadcrumb from "@/layout/topbar/components/Breadcrumb.tsx";
import {GRAMMAR_DETAIL_BREADCRUMB} from "@/layout/topbar/constants/topbar_constants.ts";
import TopBarSearch from "@/layout/topbar/components/TopBarSearch.tsx";

export default function TopBar() {

    return (
        <header className="grid grid-cols-[auto_1fr] items-center gap-4 px-5
        py-1.5 bg-[#0B1020] border-b border-[#27324B]">
            <Breadcrumb items={GRAMMAR_DETAIL_BREADCRUMB} />

            <div className="flex items-center justify-end gap-4">
                <TopBarSearch />
                <LanguageSwitcher />
            </div>
        </header>
    );
}