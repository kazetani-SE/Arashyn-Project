import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";

type Props = { open: boolean; onStay: () => void; onLeave: () => void };

export function LeaveConfirmDialog({ open, onStay, onLeave }: Props) {
    return (
        <Dialog open={open} onOpenChange={(o) => !o && onStay()}>
            <DialogContent className="bg-[#0a0a12] border border-[#1e1b3a] text-white">
                <DialogHeader>
                    <DialogTitle>Leave this session?</DialogTitle>
                    <DialogDescription className="text-neutral-400">
                        Your progress in this session is temporary. If you leave now, you will lose all of it.
                    </DialogDescription>
                </DialogHeader>
                <DialogFooter>
                    <Button variant="outline" onClick={onStay}>Keep studying</Button>
                    <Button onClick={onLeave} className="bg-red-900 text-white hover:bg-red-800">
                        Leave and lose progress
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}