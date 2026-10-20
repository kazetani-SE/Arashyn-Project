type ActionContext = {
    id: string;
    onMove: (id: string) => void;
    onCopy: (id: string) => void;
    onDelete: (id: string) => void;
    onLearning?: (id: string) => void;
    onReset?: (id: string) => void;
};

export type UserActionItem = {
    key: string;
    label: string;
    onClick: () => void;
};

export function createUserFolderActions(ctx: ActionContext): UserActionItem[] {
    return [
        {
            key: "move",
            label: "Move",
            onClick: () => ctx.onMove(ctx.id),
        },
        {
            key: "copy",
            label: "Copy",
            onClick: () => ctx.onCopy(ctx.id),
        },
        {
            key: "delete",
            label: "Delete",
            onClick: () => ctx.onDelete(ctx.id),
        },
    ];
}

export function createUserDeckActions(ctx: ActionContext): UserActionItem[] {
    return [
        {
            key: "move",
            label: "Move",
            onClick: () => ctx.onMove(ctx.id),
        },
        {
            key: "copy",
            label: "Copy",
            onClick: () => ctx.onCopy(ctx.id),
        },
        {
            key: "delete",
            label: "Delete",
            onClick: () => ctx.onDelete(ctx.id),
        },
        {
            key: "learning",
            label: "Learning",
            onClick: () => ctx.onLearning?.(ctx.id),
        },
    ];
}

export function createUserGrammarActions(ctx: ActionContext): UserActionItem[] {
    return [
        {
            key: "move",
            label: "Move",
            onClick: () => ctx.onMove(ctx.id),
        },
        {
            key: "copy",
            label: "Copy",
            onClick: () => ctx.onCopy(ctx.id),
        },
        {
            key: "delete",
            label: "Delete",
            onClick: () => ctx.onDelete(ctx.id),
        },
        {
            key: "reset",
            label: "Reset",
            onClick: () => ctx.onReset?.(ctx.id),
        },
    ];
}