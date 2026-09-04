import { Plus, Trash2 } from "lucide-react";

import { Button } from "@/components/ui/button";

import {
    emptyComponent,
    emptyMeaning,
    type ComponentFormValue,
    type GroupFormValue,
    type MeaningFormValue,
} from "@/entities/grammar/grammar_form_types";
import type { Language } from "@/shared/enum/language";

import { FormPicker } from "./FormPicker";
import MeaningFieldBlock from "@/features/create/components/MeaningFieldBlock.tsx";

interface GroupFieldBlockProps {
    language: Language;
    group: GroupFormValue;
    onChange: (next: GroupFormValue) => void;
    onRemove?: () => void;
}

export default function GroupFieldBlock({
                             language,
                             group,
                             onChange,
                             onRemove,
                         }: GroupFieldBlockProps) {
    const updateComponent = (i: number, next: ComponentFormValue) => {
        const components = [...group.components];
        components[i] = next;

        onChange({
            ...group,
            components,
        });
    };

    const addComponent = () =>
        onChange({
            ...group,
            components: [
                ...group.components,
                emptyComponent(group.components.length + 1),
            ],
        });

    const removeComponent = (i: number) =>
        onChange({
            ...group,
            components: group.components.filter((_, idx) => idx !== i),
        });

    const updateMeaning = (i: number, next: MeaningFormValue) => {
        const meanings = [...group.meanings];
        meanings[i] = next;

        onChange({
            ...group,
            meanings,
        });
    };

    const addMeaning = () =>
        onChange({
            ...group,
            meanings: [...group.meanings, emptyMeaning()],
        });

    const removeMeaning = (i: number) =>
        onChange({
            ...group,
            meanings: group.meanings.filter((_, idx) => idx !== i),
        });

    return (
        <div className="rounded-xl border border-[#1e1b3a] bg-[#12101f]/40 p-5">
            <div className="mb-4 flex items-center justify-between">
        <span className="text-xs font-medium uppercase tracking-wide text-neutral-500">
          Group {group.groupKey}
        </span>

                {onRemove && (
                    <Button
                        type="button"
                        variant="ghost"
                        size="icon"
                        onClick={onRemove}
                    >
                        <Trash2 className="size-4 text-neutral-500" />
                    </Button>
                )}
            </div>

            {/* Components */}
            <div className="mb-6 flex flex-col gap-3">
                <div className="flex items-center justify-between">
                    <span className="text-xs text-neutral-500">Structure (Components)</span>

                    <Button
                        type="button"
                        variant="outline"
                        size="sm"
                        onClick={addComponent}
                        className="gap-1.5"
                    >
                        <Plus className="size-3.5" />
                        Add component
                    </Button>
                </div>

                <div className="grid grid-cols-1 gap-x-8 gap-y-3 sm:grid-cols-2 lg:grid-cols-3">
                    {group.components.map((component, i) => (
                        <div key={i} className="relative flex items-center">
              <span className="mr-2 w-5 shrink-0 text-center text-xs text-neutral-600">
                {i + 1}
              </span>

                            <div className="min-w-0 flex-1">
                                <FormPicker
                                    language={language}
                                    formId={component.formId}
                                    keyword={component.keyword}
                                    onChange={({ formId, keyword }) =>
                                        updateComponent(i, {
                                            ...component,
                                            formId,
                                            keyword: keyword,
                                        })
                                    }
                                />
                            </div>

                            {group.components.length > 1 &&
                                i < group.components.length - 1 && (
                                    <div className="absolute -right-5 flex size-5 translate-x-1/2 items-center justify-center rounded-full border border-[#2a2748] bg-[#12101f]">
                                        <Plus className="size-3 text-neutral-500" />
                                    </div>
                                )}

                            {group.components.length > 1 && (
                                <Button
                                    type="button"
                                    variant="ghost"
                                    size="icon"
                                    className="ml-1 size-7 shrink-0"
                                    onClick={() => removeComponent(i)}
                                >
                                    <Trash2 className="size-3.5 text-neutral-600" />
                                </Button>
                            )}
                        </div>
                    ))}
                </div>
            </div>

            {/* Meanings */}
            <div className="flex flex-col gap-4 border-t border-[#1e1b3a] pt-4">
                <div className="flex items-center justify-between">
                    <span className="text-xs text-neutral-500">Meanings</span>

                    <Button
                        type="button"
                        variant="outline"
                        size="sm"
                        onClick={addMeaning}
                        className="gap-1.5"
                    >
                        <Plus className="size-3.5" />
                        Add meaning
                    </Button>
                </div>

                {group.meanings.map((meaning, mi) => (
                    <MeaningFieldBlock
                        key={mi}
                        meaning={meaning}
                        onChange={(next) => updateMeaning(mi, next)}
                        onRemove={
                            group.meanings.length > 1 ? () => removeMeaning(mi) : undefined
                        }
                    />
                ))}
            </div>
        </div>
    );
}