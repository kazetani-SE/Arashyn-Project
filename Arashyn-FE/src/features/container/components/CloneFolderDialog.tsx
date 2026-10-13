import { useState } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";
import {
    Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle,
} from "@/components/ui/dialog.tsx";
import { Button } from "@/components/ui/button.tsx";
import { UserFolderPicker } from "./UserFolderPicker";
import {userFolderService} from "@/entities/hub/userfolder/user_folder_service.ts";

type Props = {
    open: boolean;
    onOpenChange: (open: boolean) => void;
    folderId: string;
};

export function CloneFolderDialog({ open, onOpenChange, folderId }: Props) {
    const [selected, setSelected] = useState<string | null>(null);
    const queryClient = useQueryClient();

    const handleOpenChange = (next: boolean) => {
        if (!next) setSelected(null);
        onOpenChange(next);
    };

    const mutation = useMutation({
        mutationFn: () =>
            userFolderService.clone({
                folderId,
                userParentFolderId: selected ?? undefined,
            }),
        onSuccess: () => {
            toast.success("Folder cloned");
            queryClient.invalidateQueries({ queryKey: ["user-folders"] });
            handleOpenChange(false);
        },
        onError: () => toast.error("Could not clone this folder"),
    });

    return (
        <Dialog open={open} onOpenChange={handleOpenChange}>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Clone folder</DialogTitle>
                    <DialogDescription>
                        Choose a folder to put the clone in, or leave empty to add it to root.
                    </DialogDescription>
                </DialogHeader>

                <UserFolderPicker value={selected} onChange={setSelected} />

                <DialogFooter>
                    <Button
                        variant="ghost"
                        onClick={() => setSelected(null)}
                        disabled={selected === null || mutation.isPending}
                    >
                        Reset
                    </Button>
                    <Button onClick={() => mutation.mutate()} disabled={mutation.isPending}>
                        {mutation.isPending ? "Cloning..." : "OK"}
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}