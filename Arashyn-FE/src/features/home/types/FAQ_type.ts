export type FAQItem = {
    q: string;
    a: string;
};

export type FAQSectionProps = {
    items: FAQItem[];
    title?: string;
};