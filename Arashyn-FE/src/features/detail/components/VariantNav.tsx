import type { Group } from "@/lib/api/generated"

type GroupComponent = Group["components"][number]

function renderPattern(components: GroupComponent[]) {
    return [...components]
        .sort((a, b) => a.order - b.order)
        .map((c) => c.keyword ?? c.formId ?? "")
        .filter(Boolean)
        .join(" ")
}

function VariantNav({
                        groups,
                        activeGroup,
                        onSelect,
                    }: {
    groups: Group[]
    activeGroup: number
    onSelect: (groupKey: number) => void
}) {
    if (groups.length <= 1) return null

    return (
        <nav className="flex flex-col gap-1 border-t border-[#1e1b3a] pt-4">
            <span className="mb-1 text-xs font-medium uppercase tracking-wide text-neutral-600">
                Variants
            </span>

            {groups.map((group) => (
                <button
                    key={group.groupKey}
                    onClick={() => onSelect(group.groupKey)}
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
    )
}

export { VariantNav }