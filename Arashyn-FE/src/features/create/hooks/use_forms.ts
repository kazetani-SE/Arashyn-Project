import * as React from "react";

import { formService } from "@/entities/form/form_service";
import type { Form } from "@/entities/form/form_types";
import type { Language } from "@/shared/enum/language";

interface UseFormsResult {
    forms: Form[];
    loading: boolean;
    error: unknown;
}

export function useForms(language: Language): UseFormsResult {
    const [forms, setForms] = React.useState<Form[]>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [error, setError] = React.useState<unknown>(null);

    React.useEffect(() => {
        let cancelled = false;

        const loadForms = async () => {
            try {
                setLoading(true);
                setError(null);

                const res = await formService.list(language);

                if (!cancelled) {
                    setForms(res?.forms ?? []);
                }
            } catch (err) {
                if (!cancelled) {
                    console.error("Failed to load forms:", err);
                    setForms([]);
                    setError(err);
                }
            } finally {
                if (!cancelled) {
                    setLoading(false);
                }
            }
        };

        loadForms();

        return () => {
            cancelled = true;
        };
    }, [language]);

    return {
        forms,
        loading,
        error,
    };
}