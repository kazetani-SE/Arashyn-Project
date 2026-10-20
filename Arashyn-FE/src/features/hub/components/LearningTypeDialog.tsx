import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { LEARNING_TYPES, LEARNING_TYPE_META, type LearningType } from "@/features/learning/constants/learning_types.ts";

type Props = {
    open: boolean;
    onOpenChange: (open: boolean) => void;
    onSelect: (type: LearningType) => void;
};

export function LearningTypeDialog({ open, onOpenChange, onSelect }: Props) {
    const types = Object.values(LEARNING_TYPES);

    return (
        <Dialog open={open} onOpenChange={onOpenChange}>
            <DialogContent className="bg-[#0a0a12] border border-[#1e1b3a] text-white">
                <DialogHeader>
                    <DialogTitle>Choose a learning mode</DialogTitle>
                    <DialogDescription className="text-neutral-400">
                        How do you want to study this deck?
                    </DialogDescription>
                </DialogHeader>

                <div className="flex flex-col gap-2 pt-2">
                    {types.map((type) => {
                        const { label, implemented } = LEARNING_TYPE_META[type];
                        return (
                            <button
                                key={type}
                                type="button"
                                disabled={!implemented}
                                onClick={() => onSelect(type)}
                                className="flex items-center justify-between rounded-lg border border-[#1e1b3a] bg-card px-4 py-3
                                    text-left text-sm font-medium transition-all duration-200
                                    hover:bg-[#12101f] hover:border-[#312e81] hover:text-[#818cf8]
                                    disabled:cursor-not-allowed disabled:opacity-50 disabled:hover:bg-card
                                    disabled:hover:border-[#1e1b3a] disabled:hover:text-inherit"
                            >
                                <span>{label}</span>
                                {!implemented && (
                                    <span className="rounded-full bg-muted px-2 py-0.5 text-[10px] uppercase tracking-wide text-muted-foreground">
                                        Coming soon
                                    </span>
                                )}
                            </button>
                        );
                    })}
                </div>
            </DialogContent>
        </Dialog>
    );
}