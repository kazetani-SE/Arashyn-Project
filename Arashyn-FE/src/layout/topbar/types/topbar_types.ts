export type SearchType =
    | "All"
    | "Grammar"
    | "Deck"
    | "Folder"
    | "Component"
    | "Vocabulary"
    | "Sentence";

export type BreadcrumbProps = {
    items: BreadcrumbItem[];
}

type BreadcrumbItem = {
    title: string;
    href: string;
    key?: string;
}

export type QuickCreateAction = {
    title: string;
    onClick: () => void;
};

export type SearchFilter = {
    id: string;
    name: string;
};