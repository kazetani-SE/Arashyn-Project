import { useMemo, useState } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";
import {
    Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle,
} from "@/components/ui/dialog.tsx";
import { Button } from "@/components/ui/button.tsx";
import { Input } from "@/components/ui/input.tsx";
import { Checkbox } from "@/components/ui/checkbox.tsx";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { userDeckService } from "@/entities/hub/userdeck/user_deck_service.ts";
import { useGrammarDuplicates } from "@/features/container/hook/use_grammar_duplicates.ts";
import { UserFolderPicker } from "./UserFolderPicker";
import type {UserDeckCloneGrammarRequest} from "@/lib/api/generated";

type Mode = "keep" | "fresh" | "skip";

type Props = {
    open: boolean;
    onOpenChange: (open: boolean) => void;
    deckId: string;
    /** grammarId -> display name, used to label the duplicated grammars */
    grammarNames?: Record<string, string>;
};

export function CreateUserDeckDialog({ open, onOpenChange, deckId, grammarNames = {} }: Props) {
    const [name, setName] = useState("");
    const [folderId, setFolderId] = useState<string | null>(null);
    // Only overrides are stored; the default for a duplicated grammar is "keep".
    const [modes, setModes] = useState<Record<string, Mode>>({});
    const queryClient = useQueryClient();

    const { data: checks = [], isLoading, isError } = useGrammarDuplicates(deckId, open);

    const duplicates = useMemo(
        () => checks.filter((c) => c.duplicated && c.id && c.existingUserGrammarId),
        [checks]
    );

    const getMode = (id: string): Mode => modes[id] ?? "keep";

    const setOne = (id: string, mode: Mode, checked: boolean) =>
        setModes((prev) => ({ ...prev, [id]: checked ? mode : "skip" }));

    const setAll = (mode: Mode, checked: boolean) =>
        setModes(Object.fromEntries(duplicates.map((d) => [d.id!, checked ? mode : "skip"])));

    const headerState = (mode: Mode): boolean | "indeterminate" => {
        const count = duplicates.filter((d) => getMode(d.id!) === mode).length;
        if (count === 0) return false;
        return count === duplicates.length ? true : "indeterminate";
    };

    const handleOpenChange = (next: boolean) => {
        if (!next) {
            setName("");
            setFolderId(null);
            setModes({});
        }
        onOpenChange(next);
    };

    const mutation = useMutation({
        mutationFn: () => {
            const userGrammars: UserDeckCloneGrammarRequest[] = checks
                .filter((c): c is typeof c & { id: string } => !!c.id)
                .flatMap((c): UserDeckCloneGrammarRequest[] => {
                    // Not duplicated → always create a new user grammar
                    if (!c.duplicated || !c.existingUserGrammarId) {
                        return [{ grammarId: c.id }];
                    }

                    const mode = getMode(c.id);
                    if (mode === "skip") return [];
                    if (mode === "keep") {
                        return [{ sourceUserGrammarId: c.existingUserGrammarId }];
                    }
                    // fresh
                    return [{ grammarId: c.id }];
                });

            return userDeckService.clone({
                deckId,
                name: name.trim() || undefined,
                userFolderId: folderId ?? undefined,
                userGrammars,
            });
        },
        onSuccess: () => {
            toast.success("Deck cloned");
            queryClient.invalidateQueries({ queryKey: ["user-decks"] });
            handleOpenChange(false);
        },
        onError: () => toast.error("Could not clone this deck"),
    });

    return (
        <Dialog open={open} onOpenChange={handleOpenChange}>
            <DialogContent className="max-h-[90vh] overflow-y-auto">
                <DialogHeader>
                    <DialogTitle>Create a new copy</DialogTitle>
                    <DialogDescription>
                        Name it, pick a folder (optional) and decide what to do with grammars you
                        already have.
                    </DialogDescription>
                </DialogHeader>

                <Input
                    placeholder="Deck name (leave empty to use the original)"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    maxLength={200}
                />

                <UserFolderPicker value={folderId} onChange={setFolderId} />

                {isLoading && <Skeleton className="h-24 w-full" />}

                {isError && (
                    <p className="text-sm text-destructive">
                        Could not check your existing grammars.
                    </p>
                )}

                {!isLoading && !isError && duplicates.length > 0 && (
                    <div className="space-y-2">
                        <div className="space-y-1 rounded-md border bg-muted/40 px-3 py-2 text-sm">
                            <p className="font-medium">
                                You already have {duplicates.length} of these grammars. Choose one
                                for each:
                            </p>
                            <p className="text-muted-foreground">
                                <b className="text-foreground">Keep progress</b>: reuse your
                                existing grammar and keep its progress.
                            </p>
                            <p className="text-muted-foreground">
                                <b className="text-foreground">Start fresh</b>: create a new copy,
                                progress starts from zero.
                            </p>
                            <p className="text-muted-foreground">
                                Neither ticked: the grammar is skipped and not added to this deck.
                            </p>
                        </div>

                        <div className="grid grid-cols-[1fr_auto_auto] items-center gap-x-4 gap-y-1">
                            {/* header row with select-all for each column */}
                            <span className="text-xs font-medium uppercase text-muted-foreground">
                                Grammar
                            </span>
                            <label className="flex items-center gap-1.5 text-xs font-medium">
                                <Checkbox
                                    checked={headerState("keep") === true}
                                    onCheckedChange={(v) => setAll("keep", v === true)}
                                />
                                Keep progress
                            </label>
                            <label className="flex items-center gap-1.5 text-xs font-medium">
                                <Checkbox
                                    checked={headerState("fresh") === true}
                                    onCheckedChange={(v) => setAll("fresh", v === true)}
                                />
                                Start fresh
                            </label>

                            <div className="col-span-3 border-b" />
                        </div>

                        <div className="grid max-h-48 grid-cols-[1fr_auto_auto] items-center gap-x-4 gap-y-1 overflow-y-auto">
                            {duplicates.map((d) => {
                                const mode = getMode(d.id!);
                                return (
                                    <div key={d.id} className="contents">
                                        <span className="truncate py-1.5 text-sm">
                                            {grammarNames[d.id!] ?? `Grammar ${d.id!.slice(0, 8)}`}
                                        </span>
                                        <div className="flex w-[104px] justify-center">
                                            <Checkbox
                                                checked={mode === "keep"}
                                                onCheckedChange={(v) => setOne(d.id!, "keep", v === true)}
                                                aria-label="Keep progress"
                                            />
                                        </div>
                                        <div className="flex w-[92px] justify-center">
                                            <Checkbox
                                                checked={mode === "fresh"}
                                                onCheckedChange={(v) => setOne(d.id!, "fresh", v === true)}
                                                aria-label="Start fresh"
                                            />
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                )}

                <DialogFooter>
                    <Button
                        variant="ghost"
                        onClick={() => handleOpenChange(false)}
                        disabled={mutation.isPending}
                    >
                        Cancel
                    </Button>
                    <Button
                        onClick={() => mutation.mutate()}
                        disabled={mutation.isPending || isLoading || isError}
                    >
                        {mutation.isPending ? "Cloning..." : "Clone"}
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}