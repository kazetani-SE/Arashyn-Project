export type SearchType =
    | "All"
    | "Grammar"
    | "Component"
    | "Vocabulary"
    | "Sentence";

export type BreadcrumbProps = {
    items: BreadcrumbItem[];
}

type BreadcrumbItem = {
    title: string;
    href: string;
}

export type QuickCreateAction = {
    title: string;
    onClick: () => void;
};

export type SearchFilter = {
    id: string;
    name: string;
};