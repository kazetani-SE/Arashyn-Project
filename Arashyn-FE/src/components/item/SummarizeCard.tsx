import * as React from "react"
import {useNavigate} from "react-router-dom";
import {ROUTE_PATHS} from "@/app/router/route.ts";

function cn(...classes: Array<string | false | null | undefined>) {
    return classes.filter(Boolean).join(" ")
}

type SummarizePattern = {
    key: React.Key
    content: React.ReactNode
}

type SummarizeCardClassNames = {
    header?: string
    title?: string
    filter?: string
    term?: string
    meaning?: string
}

type SummarizeCardProps = React.ComponentProps<"div"> & {
    title: React.ReactNode
    filters?: string[]
    patterns: SummarizePattern[]
    meanings: React.ReactNode[]
    classNames?: SummarizeCardClassNames
}

function getFilterSizeClass(count: number) {
    if (count > 6) return "text-[10px] px-1.5 py-0.5 gap-0.5"
    if (count > 3) return "text-[11px] px-2 py-0.5 gap-1"
    return "text-xs px-2 py-1 gap-1"
}

function SummarizeCard({
                           title,
                           filters,
                           patterns,
                           meanings,
                           className,
                           classNames,
                           ...props
                       }: SummarizeCardProps) {

    const navigate = useNavigate();
    const onClickHandler = () =>{
        navigate(ROUTE_PATHS.DETAIL);
    }

    return (
        <div
            data-slot="summarize-card"
            className={cn(
                "flex min-h-60 flex-col overflow-hidden rounded-xl bg-card " +
                "text-card-foreground ring-1 ring-foreground/10 cursor-pointer",
                className
            )}
            {...props}
            onClick={onClickHandler}
        >
            <div
                data-slot="summarize-card-header"
                className={cn("flex flex-col gap-1.5 px-5 pt-4", classNames?.header)}
            >
                <div
                    data-slot="summarize-card-title"
                    className={cn(
                        "truncate text-base font-semibold text-foreground",
                        classNames?.title
                    )}
                >
                    {title}
                </div>

                {filters && filters.length > 0 && (
                    <div
                        data-slot="summarize-card-filter"
                        className={cn("flex flex-wrap gap-1", classNames?.filter)}
                    >
                        {filters.map((filter) => (
                            <span
                                key={filter}
                                className={cn(
                                    "shrink-0 rounded-full bg-muted font-medium text-muted-foreground",
                                    getFilterSizeClass(filters.length)
                                )}
                            >
                    {filter}
                </span>
                        ))}
                    </div>
                )}
            </div>

            <div
                data-slot="summarize-card-term"
                className={cn("flex flex-1 items-center justify-center px-5 py-8", classNames?.term)}
            >
                <div className="flex max-w-full flex-col items-center gap-2 overflow-x-auto">
                    {patterns.map(({ key, content }, index) => (
                        <div key={key} className="flex items-center gap-2">
                            {patterns.length > 1 && (
                                <span className="flex size-4 shrink-0 items-center justify-center rounded-full bg-muted text-[10px] font-medium text-muted-foreground">
                                    {index + 1}
                                </span>
                            )}
                            <span className="whitespace-nowrap text-xl font-medium tracking-tight">
                                {content}
                            </span>
                        </div>
                    ))}
                </div>
            </div>

            <div
                data-slot="summarize-card-meaning"
                className={cn("border-t bg-muted/40 px-5 py-4", classNames?.meaning)}
            >
                <div className="flex flex-col gap-2">
                    {meanings.map((meaning, index) => (
                        <div key={index} className="flex gap-2 text-sm leading-relaxed text-muted-foreground">
                            <span className="shrink-0 font-medium">{index + 1}.</span>
                            <span>{meaning}</span>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}

export { SummarizeCard }
export type { SummarizeCardProps, SummarizePattern, SummarizeCardClassNames }