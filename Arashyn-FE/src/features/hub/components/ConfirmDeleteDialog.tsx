import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogDescription,
    DialogFooter,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";

type ConfirmDeleteDialogProps = {
    open: boolean;
    onOpenChange: (open: boolean) => void;
    title?: string;
    description?: string;
    isLoading?: boolean;
    onConfirm: () => void | Promise<void>;
};

export function ConfirmDeleteDialog({
                                        open,
                                        onOpenChange,
                                        title = "Delete this item?",
                                        description = "This action cannot be undone.",
                                        isLoading = false,
                                        onConfirm,
                                    }: ConfirmDeleteDialogProps) {
    const handleConfirm = async () => {
        await onConfirm();
        onOpenChange(false);
    };

    return (
        <Dialog open={open} onOpenChange={onOpenChange}>
            <DialogContent className="max-w-md bg-[#0a0a12] border border-[#1e1b3a] text-white">
                <DialogHeader>
                    <DialogTitle className="text-lg font-semibold text-white">
                        {title}
                    </DialogTitle>
                    <DialogDescription className="text-neutral-400">
                        {description}
                    </DialogDescription>
                </DialogHeader>

                <DialogFooter className="gap-2 sm:gap-0">
                    <Button
                        variant="ghost"
                        onClick={() => onOpenChange(false)}
                        disabled={isLoading}
                        className="text-muted-foreground hover:text-white"
                    >
                        Cancel
                    </Button>
                    <Button
                        variant="destructive"
                        onClick={handleConfirm}
                        disabled={isLoading}
                    >
                        {isLoading ? "Deleting..." : "Delete"}
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}