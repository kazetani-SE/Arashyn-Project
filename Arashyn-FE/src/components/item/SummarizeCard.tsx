import * as React from "react"
import { useNavigate } from "react-router-dom"
import {ROUTES} from "@/app/router/route.ts"
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogFooter,
} from "@/components/ui/dialog"
import { Button } from "@/components/ui/button"

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
    grammarId: string
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
                           grammarId,
                           title,
                           filters,
                           patterns,
                           meanings,
                           className,
                           classNames,
                           ...props
                       }: SummarizeCardProps) {

    const navigate = useNavigate()
    const [open, setOpen] = React.useState(false)

    const firstPattern = patterns[0]
    const firstMeaning = meanings[0]

    const onClickHandler = () => {
        setOpen(true)
    }

    const onViewDetailHandler = () => {
        setOpen(false)
        navigate(ROUTES.grammarDetail(grammarId))
    }

    return (
        <>
            <div
                data-slot="summarize-card"
                className={cn(
                    "group flex min-h-60 flex-col overflow-hidden rounded-xl bg-card " +
                    "text-card-foreground ring-1 ring-foreground/10 cursor-pointer " +
                    "transition-all duration-200 " +
                    "hover:bg-[#12101f] hover:ring-[#1e1b3a] " +
                    "hover:shadow-[0_10px_40px_-10px_rgba(49,46,129,0.55)] " +
                    "hover:-translate-y-0.5",
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

                {/* Chỉ hiện pattern đầu tiên */}
                <div
                    data-slot="summarize-card-term"
                    className={cn("flex flex-1 items-center justify-center px-5 py-8", classNames?.term)}
                >
                    {firstPattern && (
                        <div className="flex max-w-full flex-col items-center gap-2">
                            <span
                                className="whitespace-nowrap text-xl font-medium tracking-tight
                                transition-all duration-200
                                group-hover:scale-105 group-hover:text-[#818cf8]"
                            >
                                {firstPattern.content}
                            </span>
                        </div>
                    )}
                </div>

                {/* Chỉ hiện meaning đầu tiên */}
                <div
                    data-slot="summarize-card-meaning"
                    className={cn(
                        "border-t bg-muted/40 px-5 py-4 transition-colors duration-200 group-hover:bg-[#0a0a12]/60",
                        classNames?.meaning
                    )}
                >
                    {firstMeaning !== undefined && (
                        <div className="flex gap-2 text-sm leading-relaxed text-muted-foreground">
                            <span className="shrink-0 font-medium">1.</span>
                            <span>{firstMeaning}</span>
                        </div>
                    )}
                </div>
            </div>

            {/* Dialog to, hiện đầy đủ mọi thứ khi click — tông indigo siêu tối */}
            <Dialog open={open} onOpenChange={setOpen}>
                <DialogContent
                    className="max-w-2xl max-h-[85vh] overflow-y-auto p-6
                    bg-[#0a0a12] border border-[#1e1b3a] text-white
                    shadow-[0_20px_60px_-15px_rgba(49,46,129,0.6)]"
                >
                    <DialogHeader>
                        <DialogTitle className="text-xl font-semibold text-white">
                            {title}
                        </DialogTitle>
                    </DialogHeader>

                    <div className="flex flex-col gap-6 pt-2">
                        {filters && filters.length > 0 && (
                            <div className="flex flex-wrap gap-1.5">
                                {filters.map((filter) => (
                                    <span
                                        key={filter}
                                        className="rounded-full bg-[#1e1b3a] px-2.5 py-1 text-xs font-medium text-[#a5adf0]"
                                    >
                                        {filter}
                                    </span>
                                ))}
                            </div>
                        )}

                        <div className="flex flex-col items-center gap-3 py-6">
                            {patterns.map(({ key, content }, index) => (
                                <div key={key} className="flex items-center gap-2">
                                    {patterns.length > 1 && (
                                        <span className="flex size-5 shrink-0 items-center justify-center rounded-full bg-[#1e1b3a] text-xs font-medium text-[#a5adf0]">
                                            {index + 1}
                                        </span>
                                    )}
                                    <span className="text-2xl font-medium tracking-tight text-[#818cf8]">
                                        {content}
                                    </span>
                                </div>
                            ))}
                        </div>

                        <div className="flex flex-col gap-2.5 border-t border-[#1e1b3a] pt-4">
                            {meanings.map((meaning, index) => (
                                <div key={index} className="flex gap-2 text-sm leading-relaxed text-neutral-400">
                                    <span className="shrink-0 font-medium text-neutral-300">{index + 1}.</span>
                                    <span>{meaning}</span>
                                </div>
                            ))}
                        </div>
                    </div>

                    <DialogFooter>
                        <Button
                            onClick={onViewDetailHandler}
                            className="bg-[#312e81] text-white hover:bg-[#3f3b9e] border border-[#4c4699]"
                        >
                            View detail
                        </Button>
                    </DialogFooter>
                </DialogContent>
            </Dialog>
        </>
    )
}

export { SummarizeCard }
export type { SummarizeCardProps, SummarizeCardClassNames, SummarizePattern }