import type {Example} from "@/entities/example/example_types.ts";
import type {Meaning} from "@/entities/meaning/meaning_types.ts";

function ExampleItem({ example }: { example: Example }) {
    return (
        <div className="text-sm">
            <p className="text-neutral-200">{example.sentence}</p>

            <p className="text-neutral-500">{example.translation}</p>

            {example.note && (
                <p className="mt-0.5 text-xs italic text-neutral-600">
                    {example.note}
                </p>
            )}
        </div>
    )
}

function MeaningItem({
                         meaning,
                         index,
                     }: {
    meaning: Meaning
    index: number
}) {
    return (
        <div className="rounded-xl border border-[#1e1b3a] bg-[#12101f]/50 p-5">
            <div className="flex gap-2 text-sm">
                <span className="shrink-0 font-medium text-[#a5adf0]">
                    {index + 1}.
                </span>

                <span className="text-neutral-200">
                    {meaning.content}
                </span>
            </div>

            {meaning.examples.length > 0 && (
                <div className="mt-4 flex flex-col gap-3 border-t border-[#1e1b3a] pt-4">
                    {meaning.examples.map((example) => (
                        <ExampleItem key={example.id} example={example} />
                    ))}
                </div>
            )}
        </div>
    )
}

export { MeaningItem }