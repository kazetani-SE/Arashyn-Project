import type { MeaningCreateBase } from "@/lib/api/generated"

type MeaningData = MeaningCreateBase
type ExampleData = NonNullable<MeaningData["examples"]>[number]

function ExampleItem({ example }: { example: ExampleData }) {
    return (
        <div className="text-base">
            <p className="text-neutral-200">{example.sentence}</p>

            <p className="text-neutral-400">{example.translation}</p>

            {example.note && (
                <p className="mt-0.5 text-xs italic text-neutral-500">
                    *{example.note}
                </p>
            )}
        </div>
    )
}

function MeaningItem({
                         meaning,
                         index,
                     }: {
    meaning: MeaningData
    index: number
}) {
    const examples = meaning.examples ?? []

    return (
        <div className="rounded-xl border border-[#1e1b3a] bg-[#12101f]/50 p-5">
            <div className="flex gap-2 text-base">
                <span className="shrink-0 font-medium text-[#a5adf0]">
                    {index + 1}.
                </span>

                <span className="text-neutral-200">
                    {meaning.content}
                </span>
            </div>

            {examples.length > 0 && (
                <div className="mt-4 flex flex-col gap-3 border-t border-[#1e1b3a] pt-4">
                    {examples.map((example, i) => (
                        <ExampleItem key={i} example={example} />
                    ))}
                </div>
            )}
        </div>
    )
}

export { MeaningItem }