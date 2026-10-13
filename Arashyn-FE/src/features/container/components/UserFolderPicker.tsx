import { useMemo, useState } from "react";
import { Check, Folder } from "lucide-react";
import { Input } from "@/components/ui/input.tsx";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { cn } from "@/lib/utils.ts";
import { useUserFolders } from "@/features/container/hook/use_user_folders.ts";

type Props = {
    value: string | null;
    onChange: (id: string | null) => void;
};

export function UserFolderPicker({ value, onChange }: Props) {
    const [query, setQuery] = useState("");
    const { data: folders = [], isLoading, isError } = useUserFolders();

    const filtered = useMemo(() => {
        const q = query.trim().toLowerCase();
        return q ? folders.filter((f) => f.name?.toLowerCase().includes(q)) : folders;
    }, [folders, query]);

    if (isLoading) return <Skeleton className="h-48 w-full" />;

    if (isError) {
        return (
            <p className="py-8 text-center text-sm text-muted-foreground">
                Could not load your folders.
            </p>
        );
    }

    return (
        <div className="space-y-2">
            <Input
                placeholder="Search folders..."
                value={query}
                onChange={(e) => setQuery(e.target.value)}
            />

            <div className="max-h-64 space-y-1 overflow-y-auto rounded-md border p-1">
                {filtered.length === 0 && (
                    <p className="py-6 text-center text-sm text-muted-foreground">
                        {folders.length === 0
                            ? "You don't have any folders yet. It will be added to root."
                            : "No folders found."}
                    </p>
                )}

                {filtered.map((folder) => {
                    const selected = folder.id === value;
                    return (
                        <button
                            key={folder.id}
                            type="button"
                            onClick={() => onChange(selected ? null : (folder.id ?? null))}
                            className={cn(
                                "flex w-full items-center gap-2 rounded-md px-3 py-2 text-left text-sm transition-colors hover:bg-muted",
                                selected && "bg-muted font-medium"
                            )}
                        >
                            <Folder className="h-4 w-4 shrink-0 text-muted-foreground" />
                            <span className="flex-1 truncate">{folder.name}</span>
                            {selected && <Check className="h-4 w-4 shrink-0" />}
                        </button>
                    );
                })}
            </div>
        </div>
    );
}