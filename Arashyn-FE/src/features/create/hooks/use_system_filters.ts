import * as React from "react";
import { filterService } from "@/entities/filter/filter_service.ts";
import type { Filter } from "@/entities/filter/filter_types.ts";
import type { Language } from "@/shared/enum/language.ts";

interface UseSystemFiltersResult {
    filters: Filter[];
    loading: boolean;
    error: unknown;
}

export function useSystemFilters(language: Language): UseSystemFiltersResult {
    const [filters, setFilters] = React.useState<Filter[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [error, setError] = React.useState<unknown>(null);

    React.useEffect(() => {
        let cancelled = false;

        const loadFilters = async () => {
            try {
                setLoading(true);
                setError(null);

                const res = await filterService.list(language);

                if (!cancelled) {
                    setFilters(res?.systemFilters ?? []);
                }
            } catch (err) {
                if (!cancelled) {
                    console.error("Failed to load filters:", err);
                    setFilters([]);
                    setError(err);
                }
            } finally {
                if (!cancelled) {
                    setLoading(false);
                }
            }
        };

        loadFilters();

        return () => {
            cancelled = true;
        };
    }, [language]);

    return {
        filters,
        loading,
        error,
    };
}