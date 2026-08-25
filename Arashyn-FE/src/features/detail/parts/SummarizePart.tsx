import * as React from "react"
import { Badge } from "@/components/ui/badge"
import { Globe, Lock, User } from "lucide-react"
import type { grammar_detail_response } from "@/shared/responses/grammar_detail_response.ts"
import {VariantNav} from "@/features/detail/components/VariantNav.tsx";

function SummarizePart({
                           data,
                       }: {
    data: grammar_detail_response
}) {
    const [activeGroup, setActiveGroup] = React.useState<number>(
        data.groups[0]?.groupKey ?? 1
    )

    const scrollTo = (groupKey: number) => {
        setActiveGroup(groupKey)

        document
            .getElementById(`group-${groupKey}`)
            ?.scrollIntoView({
                behavior: "smooth",
                block: "start",
            })
    }

    return (
        <aside className="md:sticky md:top-24 md:self-start">
            <h1 className="mb-2 text-2xl font-semibold tracking-tight text-indigo-50">
                {data.title}
            </h1>

            <div className="mb-4 flex flex-wrap items-center gap-2 text-xs text-neutral-500">
                <Badge className="bg-[#1e1b3a] text-[#a5adf0] hover:bg-[#1e1b3a]">
                    {data.language}
                </Badge>

                <span className="flex items-center gap-1">
                    {data.isPublic ? (
                        <Globe className="size-3" />
                    ) : (
                        <Lock className="size-3" />
                    )}

                    {data.isPublic ? "Public" : "Private"}
                </span>
            </div>

            <div className="mb-6 flex items-center gap-1.5 text-xs text-neutral-500">
                <User className="size-3" />
                {data.ownerName}
            </div>

            {data.filters.length > 0 && (
                <div className="mb-6 flex flex-wrap gap-1.5">
                    {data.filters.map((filter) => (
                        <span
                            key={filter.name}
                            className="rounded-full bg-[#1e1b3a] px-2 py-0.5 text-[11px] font-medium text-[#a5adf0]"
                        >
                            {filter.name}
                        </span>
                    ))}
                </div>
            )}

            <VariantNav
                groups={data.groups}
                activeGroup={activeGroup}
                onSelect={scrollTo}
            />
        </aside>
    )
}

export { SummarizePart }