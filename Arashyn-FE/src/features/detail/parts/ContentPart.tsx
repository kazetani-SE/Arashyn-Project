import { MeaningItem } from "@/features/detail/components/MeaningItem.tsx";
import type { GrammarDetailResponse, Group } from "@/lib/api/generated";

type GroupComponent = Group["components"][number];

function renderPattern(components: GroupComponent[]) {
    return [...components]
        .sort((a, b) => a.order - b.order)
        .map((c) => c.keyword ?? c.formId ?? "")
        .filter(Boolean)
        .join(" ")
}

function ContentPart({ data }: { data: GrammarDetailResponse }) {
    const groups = data.groups ?? []
    const notes = data.notes ?? []

    return (
        <main className="flex flex-col gap-14">
            {groups.map((group) => (
                <div
                    key={group.groupKey}
                    id={`group-${group.groupKey}`}
                    className="scroll-mt-24"
                >
                    <div className="mb-8 flex justify-center">
                        <span className="text-3xl font-medium tracking-tight text-indigo-100">
                            {renderPattern(group.components)}
                        </span>
                    </div>

                    <div className="flex flex-col gap-6">
                        {(group.meanings ?? []).map((meaning, index) => (
                            <MeaningItem key={index} meaning={meaning} index={index} />
                        ))}
                    </div>
                </div>
            ))}

            {notes.length > 0 && (
                <div className="border-t border-[#1e1b3a] pt-6">
                    <h2 className="mb-3 text-sm font-medium uppercase tracking-wide text-neutral-400">
                        Notes
                    </h2>

                    <div className="flex flex-col gap-1.5 text-sm text-neutral-300/80">
                        {notes.map((note, index) => (
                            <p key={note.id ?? index}>{note.content}</p>
                        ))}
                    </div>
                </div>
            )}
        </main>
    )
}

export { ContentPart }