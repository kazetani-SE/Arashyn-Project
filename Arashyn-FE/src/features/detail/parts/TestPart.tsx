import * as React from "react"
import { Badge } from "@/components/ui/badge"
import { Globe, Lock, User } from "lucide-react"
import type { ApiResponse } from "@/lib/api/types";
import type {grammar_detail_response} from "@/entities/grammar/grammar_types.ts";
import type {Component} from "@/entities/component/component_types.ts";

const mockResponse: ApiResponse<grammar_detail_response> = {
    data: {
        id: "6848c939-ac4d-4bf2-aa77-b6d525c76989",
        title: "～ておく / ～とく (Updated)",
        language: "JA",
        isPublic: true,
        ownerId: "7a271742-0d02-4c8c-b841-3a03d6ddab90",
        ownerName: "User01",
        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "fec787ac-b5e0-4871-9d0d-cd80b20b0701",
                        order: 1,
                        keyword: null,
                        form: "V-て",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "67323999-7ecd-4931-a5ef-f64c47d0533e",
                        order: 2,
                        keyword: "おく",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "855418db-5c95-4407-bbac-85149225894a",
                        content: "To do something in advance (preparatory action)",
                        groupKey: 1,
                        examples: [],
                    },
                    {
                        id: "bb61956d-f2ba-437c-a75f-3ee9240b6acf",
                        content: "To leave something as it is (temporary state/measure)",
                        groupKey: 1,
                        examples: [
                            {
                                id: "54f51464-1faf-4787-adab-663e95aca629",
                                sentence: "test example 1",
                                translation: "string",
                                note: "string",
                            },
                            {
                                id: "1b1ec462-59ed-40d2-ae4f-354b6554147b",
                                sentence: "test example 1",
                                translation: "string",
                                note: "string",
                            },
                            {
                                id: "e5615ff2-af70-473d-a777-4741751ad82d",
                                sentence: "test example 1",
                                translation: "string",
                                note: "string",
                            },
                        ],
                    },
                    {
                        id: "09909750-b374-4627-8f63-e5df2a262f70",
                        content: "To take care of a situation for future convenience",
                        groupKey: 1,
                        examples: [],
                    },
                ],
            },
            {
                groupKey: 2,
                components: [
                    {
                        id: "b6a820c3-0965-4ca8-9f67-b47e702cdd7b",
                        order: 1,
                        keyword: "とく",
                        form: null,
                        groupKey: 2,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "2a937d2e-7d2c-4433-ac58-d1fa37b99d0b",
                        content: "To do something in advance (Casual speech / Contraction of ~te oku)",
                        groupKey: 2,
                        examples: [],
                    },
                ],
            },
        ],
        notes: [],
        filters: [
            {
                id: "filter-n4",
                name: "N4",
            },
        ],
    },
    message: "Success",
    status: 200,
};

function renderPattern(components: Component[]) {
    return [...components]
        .sort((a, b) => a.order - b.order)
        .map((c) => c.keyword ?? c.form ?? "")
        .filter(Boolean)
        .join(" ");
}

function TestPart() {
    const { data } = mockResponse
    const [activeGroup, setActiveGroup] = React.useState<number>(
        data.groups[0]?.groupKey ?? 1
    )

    const refs = React.useRef<Record<number, HTMLDivElement | null>>({})

    const scrollTo = (groupKey: number) => {
        setActiveGroup(groupKey)
        refs.current[groupKey]?.scrollIntoView({ behavior: "smooth", block: "start" })
    }

    return (
        <div className="min-h-screen bg-[#0a0a12] text-white">

            <div className="mx-auto grid max-w-5xl grid-cols-1 gap-10 px-6 py-10 md:grid-cols-[220px_1fr]">

                {/* Sidebar */}
                <aside className="md:sticky md:top-24 md:self-start">
                    <h1 className="mb-2 text-2xl font-semibold tracking-tight text-white">
                        {data.title}
                    </h1>

                    <div className="mb-4 flex flex-wrap items-center gap-2 text-xs text-neutral-500">
                        <Badge className="bg-[#1e1b3a] text-[#a5adf0] hover:bg-[#1e1b3a]">
                            {data.language}
                        </Badge>
                        <span className="flex items-center gap-1">
                            {data.isPublic ? <Globe className="size-3" /> : <Lock className="size-3" />}
                            {data.isPublic ? "Public" : "Private"}
                        </span>
                    </div>

                    <div className="mb-6 flex items-center gap-1.5 text-xs text-neutral-500">
                        <User className="size-3" />
                        {data.ownerName}
                    </div>

                    {data.filters.length > 0 && (
                        <div className="mb-6 flex flex-wrap gap-1.5">
                            {data.filters.map((f) => (
                                <span
                                    key={f.name}
                                    className="rounded-full bg-[#1e1b3a] px-2 py-0.5 text-[11px] font-medium text-[#a5adf0]"
                                >
                                    {f.name}
                                </span>
                            ))}
                        </div>
                    )}

                    {data.groups.length > 1 && (
                        <nav className="flex flex-col gap-1 border-t border-[#1e1b3a] pt-4">
                            <span className="mb-1 text-xs font-medium uppercase tracking-wide text-neutral-600">
                                Variants
                            </span>
                            {data.groups.map((group) => (
                                <button
                                    key={group.groupKey}
                                    onClick={() => scrollTo(group.groupKey)}
                                    className={`rounded-md px-2.5 py-1.5 text-left text-sm transition-colors ${
                                        activeGroup === group.groupKey
                                            ? "bg-[#1e1b3a] text-[#a5adf0]"
                                            : "text-neutral-500 hover:text-neutral-300"
                                    }`}
                                >
                                    {renderPattern(group.components)}
                                </button>
                            ))}
                        </nav>
                    )}
                </aside>

                {/* Main content */}
                <main className="flex flex-col gap-14">
                    {data.groups.map((group) => (
                        <div
                            key={group.groupKey}
                            ref={(el) => { refs.current[group.groupKey] = el }}
                            className="scroll-mt-24"
                        >
                            <div className="mb-8 flex justify-center">
                                <span className="text-3xl font-medium tracking-tight text-[#818cf8]">
                                    {renderPattern(group.components)}
                                </span>
                            </div>

                            <div className="flex flex-col gap-6">
                                {group.meanings.map((meaning, index) => (
                                    <div
                                        key={meaning.id}
                                        className="rounded-xl border border-[#1e1b3a] bg-[#12101f] p-5"
                                    >
                                        <div className="flex gap-2 text-sm">
                                            <span className="shrink-0 font-medium text-[#a5adf0]">
                                                {index + 1}.
                                            </span>
                                            <span className="text-neutral-200">{meaning.content}</span>
                                        </div>

                                        {meaning.examples.length > 0 && (
                                            <div className="mt-4 flex flex-col gap-3 border-t border-[#1e1b3a] pt-4">
                                                {meaning.examples.map((ex) => (
                                                    <div key={ex.id} className="text-sm">
                                                        <p className="text-neutral-200">{ex.sentence}</p>
                                                        <p className="text-neutral-500">{ex.translation}</p>
                                                        {ex.note && (
                                                            <p className="mt-0.5 text-xs italic text-neutral-600">
                                                                {ex.note}
                                                            </p>
                                                        )}
                                                    </div>
                                                ))}
                                            </div>
                                        )}
                                    </div>
                                ))}
                            </div>
                        </div>
                    ))}

                    {data.notes.length > 0 && (
                        <div className="border-t border-[#1e1b3a] pt-6">
                            <h2 className="mb-3 text-sm font-medium uppercase tracking-wide text-neutral-500">
                                Notes
                            </h2>
                            <div className="flex flex-col gap-1.5 text-sm text-neutral-400">
                                {data.notes.map((note) => (
                                    <p key={note.id}>{note.content}</p>
                                ))}
                            </div>
                        </div>
                    )}
                </main>
            </div>
        </div>
    )
}

export { TestPart }
export type { ApiResponse }