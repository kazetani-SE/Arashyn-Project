import type { Language } from "@/shared/enum/language.ts";
import { Checkbox } from "@/components/ui/checkbox";
import {useSystemFilters} from "@/features/create/hooks/use_system_filters.ts";

interface FilterSelectorProps {
    language: Language;
    selectedIds: string[];
    onChange: (ids: string[]) => void;
}

export function FilterSelector({
                                   language,
                                   selectedIds,
                                   onChange,
                               }: FilterSelectorProps) {
    const { filters, loading } = useSystemFilters(language);

    const toggle = (id: string) => {
        onChange(
            selectedIds.includes(id)
                ? selectedIds.filter((x) => x !== id)
                : [...selectedIds, id]
        );
    };

    if (loading) {
        return (
            <div className="text-xs text-neutral-500">
                Loading filters...
            </div>
        );
    }

    if (filters.length === 0) {
        return null;
    }

    return (
        <div className="flex flex-col gap-1.5">
            <span className="text-xs font-medium uppercase tracking-wide text-neutral-500">
                Filters
            </span>

            <div className="flex flex-wrap gap-3">
                {filters.map((filter) => (
                    <label
                        key={filter.id}
                        className="flex items-center gap-1.5 text-xs text-neutral-300 cursor-pointer"
                    >
                        <Checkbox
                            checked={selectedIds.includes(filter.id)}
                            onCheckedChange={() => toggle(filter.id)}
                        />
                        {filter.name}
                    </label>
                ))}
            </div>
        </div>
    );
}