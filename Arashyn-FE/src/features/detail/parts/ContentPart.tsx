import {MeaningItem} from "@/features/detail/components/MeaningItem.tsx";
import type {grammar_detail_response} from "@/entities/grammar/grammar_types.ts";
import type {Component} from "@/entities/component/component_types.ts";

function renderPattern(components: Component[]) {
    return [...components]
        .sort((a, b) => a.order - b.order)
        .map((c) => c.keyword ?? c.form ?? "")
        .filter(Boolean)
        .join(" ")
}

function ContentPart({
                         data,
                     }: {
    data: grammar_detail_response
}) {
    return (
        <main className="flex flex-col gap-14">
            {data.groups.map((group) => (
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
                        {group.meanings.map((meaning, index) => (
                            <MeaningItem key={meaning.id} meaning={meaning} index={index} />
                        ))}
                    </div>
                </div>
            ))}

            {data.notes.length > 0 && (
                <div className="border-t border-[#1e1b3a] pt-6">
                    <h2 className="mb-3 text-sm font-medium uppercase tracking-wide text-neutral-400">
                        Notes
                    </h2>

                    <div className="flex flex-col gap-1.5 text-sm text-neutral-300/80">
                        {data.notes.map((note) => (
                            <p key={note.id}>
                                {note.content}
                            </p>
                        ))}
                    </div>
                </div>
            )}
        </main>
    )
}

export { ContentPart }