import { useState } from "react";
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { CreateFillBlankTestRequestDifficultyEnum } from "@/lib/api/generated";
import type { CreateFillBlankTestRequest } from "@/lib/api/generated";

export type FillBlankSetting = Omit<CreateFillBlankTestRequest, "userDeckId">;

type Props = {
    open: boolean;
    isLoading: boolean;
    errorMessage?: string;
    onSubmit: (setting: FillBlankSetting) => void;
    onCancel: () => void;
};

const DIFFICULTIES = [
    { value: CreateFillBlankTestRequestDifficultyEnum.Easy, label: "Easy" },
    { value: CreateFillBlankTestRequestDifficultyEnum.Medium, label: "Medium" },
    { value: CreateFillBlankTestRequestDifficultyEnum.Hard, label: "Hard" },
];

export function FillBlankSettingDialog({ open, isLoading, errorMessage, onSubmit, onCancel }: Props) {
    const [difficulty, setDifficulty] = useState<CreateFillBlankTestRequestDifficultyEnum>(
        CreateFillBlankTestRequestDifficultyEnum.Easy
    );
    const [numOfQuestion, setNumOfQuestion] = useState(10);
    const [hasForm, setHasForm] = useState(true);
    const [hasKeyword, setHasKeyword] = useState(true);

    const handleSubmit = () => onSubmit({ difficulty, numOfQuestion, hasForm, hasKeyword });

    return (
        <Dialog open={open} onOpenChange={(o) => !o && !isLoading && onCancel()}>
            <DialogContent className="bg-[#0a0a12] border border-[#1e1b3a] text-white">
                <DialogHeader>
                    <DialogTitle>Fill in the blank</DialogTitle>
                    <DialogDescription className="text-neutral-400">
                        Choose the settings for this test.
                    </DialogDescription>
                </DialogHeader>

                <div className="flex flex-col gap-5 pt-2">
                    <div className="space-y-2">
                        <div className="text-sm font-medium">Difficulty</div>
                        <div className="grid grid-cols-3 gap-2">
                            {DIFFICULTIES.map((d) => (
                                <button
                                    key={d.value}
                                    type="button"
                                    onClick={() => setDifficulty(d.value)}
                                    className={`rounded-lg border px-3 py-2 text-sm transition-colors ${
                                        difficulty === d.value
                                            ? "border-[#4c4699] bg-[#312e81] text-white"
                                            : "border-[#1e1b3a] bg-card text-muted-foreground hover:bg-[#12101f]"
                                    }`}
                                >
                                    {d.label}
                                </button>
                            ))}
                        </div>
                    </div>

                    <div className="space-y-2">
                        <label htmlFor="num-question" className="text-sm font-medium">
                            Number of questions
                        </label>
                        <input
                            id="num-question"
                            type="number"
                            min={1}
                            value={numOfQuestion}
                            onChange={(e) => setNumOfQuestion(Math.max(1, Number(e.target.value) || 1))}
                            className="w-full rounded-lg border border-[#1e1b3a] bg-card px-3 py-2 text-sm outline-none focus:border-[#4c4699]"
                        />
                    </div>

                    <div className="space-y-2">
                        <label className="flex cursor-pointer items-center gap-2 text-sm">
                            <input type="checkbox" checked={hasForm} onChange={(e) => setHasForm(e.target.checked)} />
                            Include form
                        </label>
                        <label className="flex cursor-pointer items-center gap-2 text-sm">
                            <input type="checkbox" checked={hasKeyword} onChange={(e) => setHasKeyword(e.target.checked)} />
                            Include keyword
                        </label>
                    </div>
                </div>

                {errorMessage && <p className="text-sm text-red-400">{errorMessage}</p>}

                <DialogFooter>
                    <Button variant="outline" onClick={onCancel} disabled={isLoading}>Cancel</Button>
                    <Button
                        onClick={handleSubmit}
                        disabled={isLoading}
                        className="bg-[#312e81] text-white hover:bg-[#3f3b9e] border border-[#4c4699]"
                    >
                        {isLoading ? "Creating..." : "Start"}
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}