import {emptyExample, type MeaningFormValue} from "@/entities/grammar/grammar_form_types.ts";
import {Textarea} from "@/components/ui/textarea.tsx";
import {Switch} from "@/components/ui/switch.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Plus, Trash2} from "lucide-react";
import {Input} from "@/components/ui/input.tsx";

export default function MeaningFieldBlock({
                               meaning,
                               onChange,
                               onRemove,
                           }: {
    meaning: MeaningFormValue;
    onChange: (next: MeaningFormValue) => void;
    onRemove?: () => void;
}) {
    const updateExample = (
        i: number,
        next: MeaningFormValue["examples"][number]
    ) => {
        const examples = [...meaning.examples];
        examples[i] = next;

        onChange({
            ...meaning,
            examples,
        });
    };

    const addExample = () =>
        onChange({
            ...meaning,
            examples: [...meaning.examples, emptyExample()],
        });

    const removeExample = (i: number) =>
        onChange({
            ...meaning,
            examples: meaning.examples.filter((_, idx) => idx !== i),
        });

    return (
        <div className="rounded-lg border border-[#1e1b3a] bg-[#0a0a12] p-4">
            <div className="mb-3 flex items-start gap-2">
                <Textarea
                    value={meaning.content}
                    onChange={(e) =>
                        onChange({
                            ...meaning,
                            content: e.target.value,
                        })
                    }
                    placeholder="Meaning content"
                    className="flex-1"
                    required
                />

                <label className="flex items-center gap-1.5 pt-2 text-xs text-neutral-400">
                    <Switch
                        checked={meaning.isPublic}
                        onCheckedChange={(checked) =>
                            onChange({
                                ...meaning,
                                isPublic: checked,
                            })
                        }
                    />
                    Public
                </label>

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

            <div className="flex flex-col gap-2 pl-4">
                <div className="flex items-center justify-between">
                    <span className="text-xs text-neutral-600">Examples</span>

                    <Button
                        type="button"
                        variant="ghost"
                        size="sm"
                        onClick={addExample}
                        className="gap-1.5 text-xs"
                    >
                        <Plus className="size-3" />
                        Add example
                    </Button>
                </div>

                {meaning.examples.map((ex, i) => (
                    <div
                        key={i}
                        className="flex flex-col gap-1.5 rounded-md border border-[#1e1b3a] p-3"
                    >
                        <div className="flex items-center gap-2">
                            <Input
                                value={ex.sentence}
                                onChange={(e) =>
                                    updateExample(i, {
                                        ...ex,
                                        sentence: e.target.value,
                                    })
                                }
                                placeholder="Example sentence"
                                className="flex-1"
                            />

                            <Button
                                type="button"
                                variant="ghost"
                                size="icon"
                                onClick={() => removeExample(i)}
                            >
                                <Trash2 className="size-4 text-neutral-500" />
                            </Button>
                        </div>

                        <Input
                            value={ex.translation}
                            onChange={(e) =>
                                updateExample(i, {
                                    ...ex,
                                    translation: e.target.value,
                                })
                            }
                            placeholder="Translation"
                        />

                        <Input
                            value={ex.note}
                            onChange={(e) =>
                                updateExample(i, {
                                    ...ex,
                                    note: e.target.value,
                                })
                            }
                            placeholder="Note (optional)"
                        />
                    </div>
                ))}
            </div>
        </div>
    );
}