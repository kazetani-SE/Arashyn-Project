import {
    ChevronDown,
    ChevronRight,
    Folder,
    FolderOpen,
    Layers,
    Plus,
} from "lucide-react";
import { FOLDERS } from "@/layout/sidebar/constants/folders_constant.ts";

type SidebarFoldersProps = {
    expanded: Record<string, boolean>;
    onToggle: (folderId: string) => void;
};

export default function SidebarFolders({
                                           expanded,
                                           onToggle,
                                       }: SidebarFoldersProps) {
    return (
        <div className="flex min-h-0 flex-1 flex-col px-4 py-4">
            <div className="mb-3 flex shrink-0 items-center justify-between">
                <span className="text-xs font-medium uppercase tracking-wider text-slate-400">
                    My folders
                </span>

                <button className="rounded-md p-1 text-slate-300 transition hover:bg-indigo-500/10 hover:text-indigo-200">
                    <Plus className="size-4" />
                </button>
            </div>

            <div className="scrollbar-hide flex min-h-0 flex-1 flex-col space-y-1 overflow-y-auto">
                {FOLDERS.map((folder) => {
                    const open = expanded[folder.id];

                    return (
                        <div key={folder.id}>
                            <button
                                onClick={() => onToggle(folder.id)}
                                className={`flex w-full items-center gap-2 rounded-lg px-2 py-2 transition hover:bg-indigo-500/10 ${
                                    open
                                        ? "text-indigo-400"
                                        : "text-slate-300"
                                }`}
                            >
                                {open ? (
                                    <ChevronDown className="size-4" />
                                ) : (
                                    <ChevronRight className="size-4" />
                                )}

                                {open ? (
                                    <FolderOpen className="size-4" />
                                ) : (
                                    <Folder className="size-4" />
                                )}

                                <span className="text-sm">
                                    {folder.name}
                                </span>
                            </button>

                            {open && (
                                <div className="ml-7 mt-1 space-y-1">
                                    {folder.decks.map((deck) => (
                                        <button
                                            key={deck.id}
                                            className="flex w-full items-center gap-2 rounded-md px-2 py-1.5 text-slate-300 transition hover:bg-indigo-500/10 hover:text-indigo-200"
                                        >
                                            <Layers className="size-3.5 text-indigo-400" />

                                            <span className="flex-1 text-left text-sm">
                                                {deck.name}
                                            </span>

                                            <span className="rounded bg-secondary px-2 py-0.5 text-xs text-secondary-foreground">
                                                {deck.count}
                                            </span>
                                        </button>
                                    ))}
                                </div>
                            )}
                        </div>
                    );
                })}
            </div>
        </div>
    );
}