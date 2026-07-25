import { Search, ChevronDown, SlidersHorizontal, X } from "lucide-react";
import { useEffect, useRef, useState } from "react";
import type { SearchFilter, SearchType } from "../types/topbar_types";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover.tsx";
import { Button } from "@/components/ui/button.tsx";
import { Input } from "@/components/ui/input.tsx";
import { Separator } from "@/components/ui/separator.tsx";
import { Badge } from "@/components/ui/badge.tsx";
import { Checkbox } from "@/components/ui/checkbox.tsx";
import {SEARCH_TYPES} from "@/layout/topbar/constants/topbar_constants.ts";
import {useNavigate} from "react-router-dom";
import {ROUTE_PATHS} from "@/app/router/route.ts";

export default function TopBarSearch() {
    const navigate = useNavigate();

    const [expanded, setExpanded] = useState(false);
    const [typeOpen, setTypeOpen] = useState(false);
    const [filterOpen, setFilterOpen] = useState(false);
    const [search, setSearch] = useState("");
    const [type, setType] = useState<SearchType>("All");
    const [filters, setFilters] = useState<SearchFilter[]>([
        { id: "jp", name: "Japanese" },
        { id: "n3", name: "JLPT N3" },
    ]);

    const containerRef = useRef<HTMLDivElement>(null);
    const inputRef = useRef<HTMLInputElement>(null);

    const handleSearch = () => {
        const trimmed = search.trim();
        if (!trimmed) return;

        navigate(`${ROUTE_PATHS.DISCOVER}?q=${encodeURIComponent(trimmed)}`);
    };

    useEffect(() => {
        function handlePointerDown(e: MouseEvent) {
            const inside = containerRef.current?.contains(e.target as Node);
            if (inside || typeOpen || filterOpen) return;
            setExpanded(false);
        }
        document.addEventListener("mousedown", handlePointerDown);
        return () => document.removeEventListener("mousedown", handlePointerDown);
    }, [typeOpen, filterOpen]);

    const isOpen = expanded || typeOpen || filterOpen;

    useEffect(() => {
        if (isOpen && inputRef.current) {
            setTimeout(() => {
                inputRef.current?.focus();
            }, 10);
        }
    }, [isOpen, filters.length, type]);

    return (
        <div
            ref={containerRef}
            className={`transition-all duration-300 ${
                isOpen ? "w-full max-w-[48vw]" : "w-[360px]"
            }`}
        >
            <div
                className={`flex items-center border bg-[#161E33] transition-all duration-300 ease-out ${
                    isOpen
                        ? "h-10 rounded-full px-4 border-indigo-500 shadow-[0_0_0_2px_rgba(99,102,241,.15)]"
                        : "h-10 rounded-full px-3.5 border-[#27324B] hover:border-[#37456B]"
                }`}
            >
                <button
                    type="button"
                    onClick={handleSearch}
                    className="mr-3 shrink-0 cursor-pointer"
                >
                    <Search
                        size={isOpen ? 18 : 15}
                        className="text-slate-500 transition-all duration-300"
                    />
                </button>

                {isOpen && (
                    <>
                        <SearchTypePopover
                            value={type}
                            onChange={setType}
                            open={typeOpen}
                            onOpenChange={setTypeOpen}
                        />
                        <Separator orientation="vertical" className="mx-3 h-5" />
                    </>
                )}

                <Input
                    ref={inputRef}
                    value={search}
                    onFocus={() => setExpanded(true)}
                    onChange={(e) => setSearch(e.target.value)}
                    onKeyDown={(e) => {
                        if (e.key === "Enter") {
                            handleSearch();
                        }
                    }}
                    placeholder="Search..."
                    className="border-0 bg-transparent p-0 shadow-none focus-visible:ring-0"
                />

                {search && (
                    <button
                        type="button"
                        className="ml-2"
                        onClick={() => setSearch("")}
                    >
                        <X size={14} className="text-slate-500" />
                    </button>
                )}

                {isOpen && (
                    <>
                        <div className="ml-3 flex items-center gap-2 w-xl
                                        overflow-x-auto whitespace-nowrap scrollbar-hide
                        ">
                            {filters.map((filter) => (
                                <Badge key={filter.id}
                                       variant="secondary"
                                       className="
                                            gap-1
                                            border border-indigo-500/20
                                            bg-indigo-500/15
                                            text-indigo-200
                                            hover:bg-indigo-500/20
                                        ">
                                    {filter.name}
                                    <button
                                        type="button"
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            setFilters((prev) =>
                                                prev.filter((x) => x.id !== filter.id)
                                            );
                                        }}
                                    >
                                        <X size={12} />
                                    </button>
                                </Badge>
                            ))}
                        </div>

                        <Separator orientation="vertical" className="mx-3 h-5" />

                        <SearchFilterPopover
                            value={filters}
                            onChange={setFilters}
                            open={filterOpen}
                            onOpenChange={setFilterOpen}
                        />
                    </>
                )}
            </div>
        </div>
    );
}

type PopoverProps = {
    value: SearchType;
    onChange: (value: SearchType) => void;
    open: boolean;
    onOpenChange: (open: boolean) => void;
};

function SearchTypePopover({ value, onChange, open, onOpenChange }: PopoverProps) {
    return (
        <Popover open={open} onOpenChange={onOpenChange}>
            <PopoverTrigger>
                <Button
                    type="button"
                    variant="ghost"
                    className="h-8 gap-1 rounded-lg px-2 text-sm hover:bg-[#1E2A45] cursor-pointer"
                    tabIndex={-1}
                    onMouseDown={(e) => e.preventDefault()}
                >
                    {value}
                    <ChevronDown size={14} />
                </Button>
            </PopoverTrigger>
            <PopoverContent align="start" className="w-44 rounded-xl border-[#27324B] bg-[#161E33] p-1">
                {SEARCH_TYPES.map((item) => (
                    <Button
                        key={item}
                        variant="ghost"
                        className={`h-9 w-full justify-start rounded-lg ${
                            item === value ? "bg-indigo-500/15 text-indigo-400" : ""
                        }`}
                        onClick={() => onChange(item as SearchType)}
                    >
                        {item}
                    </Button>
                ))}
            </PopoverContent>
        </Popover>
    );
}

type FilterOption = { id: string; name: string };

const FILTER_OPTIONS: FilterOption[] = [
    { id: "jp", name: "Japanese" },
    { id: "kr", name: "Korean" },
    { id: "cn", name: "Chinese" },
    { id: "n5", name: "JLPT N5" },
    { id: "n4", name: "JLPT N4" },
    { id: "n3", name: "JLPT N3" },
];

type FilterPopoverProps = {
    value: SearchFilter[];
    onChange: (filters: SearchFilter[]) => void;
    open: boolean;
    onOpenChange: (open: boolean) => void;
};

function SearchFilterPopover({ value, onChange, open, onOpenChange }: FilterPopoverProps) {
    function toggle(option: FilterOption) {
        const exists = value.some((x) => x.id === option.id);
        if (exists) {
            onChange(value.filter((x) => x.id !== option.id));
            return;
        }
        onChange([...value, { id: option.id, name: option.name }]);
    }

    return (
        <Popover open={open} onOpenChange={onOpenChange}>
            <PopoverTrigger>
                <Button
                    type="button"
                    variant="outline"
                    className="h-8 gap-2 rounded-lg"
                    tabIndex={-1}
                    onMouseDown={(e) => e.preventDefault()}
                >
                    <SlidersHorizontal size={14} />
                    Filter
                </Button>
            </PopoverTrigger>
            <PopoverContent align="end" className="w-fit rounded-xl p-x-2">
                <div className="px-2 py-1 text-sm font-medium">Filters</div>
                <Separator className="my-1" />
                <div className="grid grid-cols-6 gap-1 mt-[-3vh]">
                    {FILTER_OPTIONS.map((item) => {
                        const checked = value.some((x) => x.id === item.id);
                        return (
                            <button
                                key={item.id}
                                onClick={() => toggle(item)}
                                className="flex w-full items-center gap-3 rounded-lg px-2 py-2 text-left hover:bg-muted"
                            >
                                <Checkbox checked={checked} />
                                <span className="text-sm">{item.name}</span>
                            </button>
                        );
                    })}
                </div>
            </PopoverContent>
        </Popover>
    );
}