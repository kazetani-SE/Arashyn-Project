import { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { Copy } from "lucide-react";
import { toast } from "sonner";
import { Button } from "@/components/ui/button.tsx";
import { CloneFolderDialog } from "./CloneFolderDialog";
import { ReuseUserDeckDialog } from "./ReuseUserDeckDialog";
import { CreateUserDeckDialog } from "./CreateUserDeckDialog";
import { userDeckService } from "@/entities/hub/userdeck/user_deck_service.ts";

type Props = {
    type: "deck" | "folder";
    id: string;
    grammarNames?: Record<string, string>;
};

export function CloneButton({ type, id, grammarNames }: Props) {
    const [folderDialogOpen, setFolderDialogOpen] = useState(false);
    const [reuseDialogOpen, setReuseDialogOpen] = useState(false);
    const [createDialogOpen, setCreateDialogOpen] = useState(false);
    const [existingUserDeckId, setExistingUserDeckId] = useState<string | null>(null);

    const checkMutation = useMutation({
        mutationFn: () => userDeckService.checkDuplicate({ deckId: id }),
        onSuccess: (res) => {
            if (res.duplicated && res.existingUserDeckId) {
                setExistingUserDeckId(res.existingUserDeckId);
                setReuseDialogOpen(true);
            } else {
                setCreateDialogOpen(true);
            }
        },
        onError: () => toast.error("Could not check your library"),
    });

    const handleClick = () => {
        if (type === "folder") setFolderDialogOpen(true);
        else checkMutation.mutate();
    };

    return (
        <>
            <Button
                onClick={handleClick}
                disabled={checkMutation.isPending}
                className="gap-1.5 bg-indigo-600 rounded-lg text-white hover:bg-indigo-700 focus:outline-none"
            >
                <Copy className="h-4 w-4" />
                {checkMutation.isPending ? "Checking..." : "Clone"}
            </Button>

            {type === "folder" && (
                <CloneFolderDialog
                    open={folderDialogOpen}
                    onOpenChange={setFolderDialogOpen}
                    folderId={id}
                />
            )}

            {type === "deck" && (
                <>
                    {existingUserDeckId && (
                        <ReuseUserDeckDialog
                            open={reuseDialogOpen}
                            onOpenChange={setReuseDialogOpen}
                            deckId={id}
                            existingUserDeckId={existingUserDeckId}
                            onDecline={() => setCreateDialogOpen(true)}
                        />
                    )}

                    <CreateUserDeckDialog
                        open={createDialogOpen}
                        onOpenChange={setCreateDialogOpen}
                        deckId={id}
                        grammarNames={grammarNames}
                    />
                </>
            )}
        </>
    );
}