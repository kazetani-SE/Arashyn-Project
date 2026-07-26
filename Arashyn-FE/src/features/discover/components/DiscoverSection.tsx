import {Button} from "@/components/ui/button.tsx";
import {Card, CardContent, CardHeader} from "@/components/ui/card.tsx";
import {Skeleton} from "@/components/ui/skeleton.tsx";
import type {SectionProps} from "@/features/discover/types/props.ts";

export default function DiscoverSection({
                                            title,
                                            icon: Icon,
                                            iconClassName,
                                            showViewAll = true,
                                            onViewAll,
                                        }: SectionProps) {
    return (
        <section className="space-y-4">
            {showViewAll && (<div className="flex items-center justify-between">
                <div className="flex items-center gap-2">
                    <Icon className={iconClassName}/>
                    <h2 className="text-xl font-semibold">{title}</h2>
                </div>

                <Button variant="ghost" onClick={onViewAll}>
                    View all
                </Button>
            </div>
            )}

            <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
                {Array.from({ length: 6 }).map((_, index) => (
                    <Card key={index}>
                        <CardHeader>
                            <Skeleton className="h-5 w-2/3" />
                        </CardHeader>

                        <CardContent className="space-y-4">
                            <Skeleton className="h-4 w-full" />
                            <Skeleton className="h-4 w-5/6" />

                            <div className="flex gap-2 pt-2">
                                <Skeleton className="h-6 w-16 rounded-full" />
                                <Skeleton className="h-6 w-20 rounded-full" />
                            </div>

                            <div className="flex justify-between pt-4">
                                <Skeleton className="h-4 w-16" />
                                <Skeleton className="h-4 w-20" />
                            </div>
                        </CardContent>
                    </Card>
                ))}
            </div>
        </section>
    );
}