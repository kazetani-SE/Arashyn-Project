import type { ReactNode } from "react";

type LibrarySectionPartProps = {
    title: string;
    count: number;
    children: ReactNode;
};

export default function ContainerSectionPart({
                                               title,
                                               count,
                                               children,
                                           }: LibrarySectionPartProps) {
    if (count === 0) return null;

    return (
        <section className="space-y-4">
            <h2 className="text-xl font-semibold">
                {title}{" "}
                <span className="text-sm font-normal text-muted-foreground">
                    ({count})
                </span>
            </h2>

            <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
                {children}
            </div>
        </section>
    );
}