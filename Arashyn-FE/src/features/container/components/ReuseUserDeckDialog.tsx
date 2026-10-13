import { useState } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";
import {
    Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle,
} from "@/components/ui/dialog.tsx";
import { Button } from "@/components/ui/button.tsx";
import { UserFolderPicker } from "./UserFolderPicker";
import { userDeckService } from "@/entities/hub/userdeck/user_deck_service.ts";

type Props = {
    open: boolean;
    onOpenChange: (open: boolean) => void;
    deckId: string;
    existingUserDeckId: string;
    onDecline: () => void;
};

export function ReuseUserDeckDialog({
                                        open, onOpenChange, deckId, existingUserDeckId, onDecline,
                                    }: Props) {
    const [selected, setSelected] = useState<string | null>(null);
    const queryClient = useQueryClient();

    const handleOpenChange = (next: boolean) => {
        if (!next) setSelected(null);
        onOpenChange(next);
    };

    const mutation = useMutation({
        mutationFn: () =>
            userDeckService.clone({
                deckId,
                sourceUserDeckId: existingUserDeckId,
                userFolderId: selected ?? undefined,
                // TODO: userGrammars (keep per-grammar progress)
            }),
        onSuccess: () => {
            toast.success("Deck added with your existing progress");
            queryClient.invalidateQueries({ queryKey: ["user-decks"] });
            handleOpenChange(false);
        },
        onError: () => toast.error("Could not clone this deck"),
    });

    return (
        <Dialog open={open} onOpenChange={handleOpenChange}>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle>You already have this deck</DialogTitle>
                    <DialogDescription>
                        Reuse your existing copy? Your progress will be kept. Optionally pick a
                        folder to put it in.
                    </DialogDescription>
                </DialogHeader>

                <UserFolderPicker value={selected} onChange={setSelected} />

                <DialogFooter className="gap-2 sm:justify-between">
                    <Button
                        variant="outline"
                        onClick={() => {
                            handleOpenChange(false);
                            onDecline();
                        }}
                        disabled={mutation.isPending}
                    >
                        No, create new
                    </Button>
                    <div className="flex gap-2">
                        <Button
                            variant="ghost"
                            onClick={() => setSelected(null)}
                            disabled={selected === null || mutation.isPending}
                        >
                            Reset
                        </Button>
                        <Button onClick={() => mutation.mutate()} disabled={mutation.isPending}>
                            {mutation.isPending ? "Cloning..." : "Use existing"}
                        </Button>
                    </div>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}