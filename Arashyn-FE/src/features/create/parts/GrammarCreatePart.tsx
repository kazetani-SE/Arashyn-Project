import { Input } from "@/components/ui/input.tsx"
import { Textarea } from "@/components/ui/textarea.tsx"
import { Button } from "@/components/ui/button.tsx"
import { Plus, Trash2 } from "lucide-react"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select.tsx"
import { FilterSelector } from "../components/FilterSelector.tsx"
import GroupFieldBlock from "@/features/create/components/GroupFieldBlock.tsx"
import {useCreateGrammar} from "@/features/create/hooks/use_create_grammar.ts";
import {useLanguageStore} from "@/shared/store/language_store.ts";

function GrammarCreatePart() {
    const language_chosen = useLanguageStore((state) => state.language)

    const {
        values,
        language,
        submitting,
        error,
        updateTitle,
        updateGroup,
        addGroup,
        removeGroup,
        updateNote,
        addNote,
        removeNote,
        updateFilterIds,
        handleSubmit,
    } = useCreateGrammar(language_chosen)

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-10">
            <section className="flex flex-col gap-4 rounded-xl border border-[#1e1b3a] bg-[#12101f]/40 p-5">
                <div className="flex flex-col gap-1.5">
                    <label className="text-xs font-medium uppercase tracking-wide text-neutral-500">Title</label>
                    <Input
                        value={values.title}
                        onChange={(e) => updateTitle(e.target.value)}
                        placeholder="Example: ～ておく / ～とく"
                        required
                    />
                </div>

                <FilterSelector
                    language={language}
                    selectedIds={values.filterIds}
                    onChange={updateFilterIds}
                />
            </section>

            <section className="flex flex-col gap-6">
                <div className="flex items-center justify-between">
                    <h2 className="text-sm font-medium uppercase tracking-wide text-neutral-500">Groups</h2>
                    <Button type="button" variant="outline" size="sm" onClick={addGroup} className="gap-1.5">
                        <Plus className="size-3.5" /> Add group
                    </Button>
                </div>

                {values.groups.map((group, index) => (
                    <GroupFieldBlock
                        key={index}
                        language={language}
                        group={group}
                        onChange={(next) => updateGroup(index, next)}
                        onRemove={values.groups.length > 1 ? () => removeGroup(index) : undefined}
                    />
                ))}
            </section>

            <section className="flex flex-col gap-4 border-t border-[#1e1b3a] pt-6">
                <div className="flex items-center justify-between">
                    <h2 className="text-sm font-medium uppercase tracking-wide text-neutral-500">Notes</h2>
                    <Button type="button" variant="outline" size="sm" onClick={addNote} className="gap-1.5">
                        <Plus className="size-3.5" /> Add note
                    </Button>
                </div>

                {values.notes.map((note, index) => (
                    <div key={index} className="flex items-start gap-2">
                        <Select
                            value={String(note.groupKey)}
                            onValueChange={(v) => updateNote(index, { ...note, groupKey: Number(v) })}
                        >
                            <SelectTrigger className="w-28 shrink-0">
                                <SelectValue />
                            </SelectTrigger>
                            <SelectContent>
                                {values.groups.map((g) => (
                                    <SelectItem key={g.groupKey} value={String(g.groupKey)}>
                                        Group {g.groupKey}
                                    </SelectItem>
                                ))}
                            </SelectContent>
                        </Select>
                        <Textarea
                            value={note.content}
                            onChange={(e) => updateNote(index, { ...note, content: e.target.value })}
                            placeholder="Note content"
                            className="flex-1"
                        />
                        <Button type="button" variant="ghost" size="icon" onClick={() => removeNote(index)}>
                            <Trash2 className="size-4 text-neutral-500" />
                        </Button>
                    </div>
                ))}
            </section>

            {error && (
                <p className="text-sm text-red-500">{error}</p>
            )}

            <div className="flex justify-end gap-3 border-t border-[#1e1b3a] pt-6">
                <Button type="submit" disabled={submitting}>
                    {submitting ? "Creating..." : "Create"}
                </Button>
            </div>
        </form>
    )
}

export { GrammarCreatePart }