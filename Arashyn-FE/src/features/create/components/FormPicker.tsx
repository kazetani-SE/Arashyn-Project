import * as React from "react";
import type { Language } from "@/shared/enum/language";

import { Input } from "@/components/ui/input";
import {useForms} from "@/features/create/hooks/use_forms.ts";

interface FormPickerProps {
    language: Language;
    formId: string | null;
    keyword: string | null;
    onChange: (value: { formId: string | null; keyword: string | null }) => void;
    disabled?: boolean;
}

function FormPicker({
                        language,
                        formId,
                        keyword,
                        onChange,
                        disabled,
                    }: FormPickerProps) {
    const { forms, loading } = useForms(language);


    const selectedForm = forms.find((form) => form.id === formId);
    const displayValue = selectedForm?.name ?? keyword ?? "";

    const listId = React.useId();

    const handleInputChange = (value: string) => {
        const matched = forms.find((f) => f.name === value);

        if (matched) {
            onChange({ formId: matched.id, keyword: null });
        } else {
            onChange({ formId: null, keyword: value });
        }
    };

    return (
        <div className="w-full">
            <Input
                list={listId}
                value={displayValue}
                onChange={(e) => handleInputChange(e.target.value)}
                placeholder={loading ? "Loading..." : "Select form or enter keyword"}
                disabled={disabled || loading}
            />
            <datalist id={listId}>
                {forms.map((form) => (
                    <option key={form.id} value={form.name} />
                ))}
            </datalist>
        </div>
    );
}

export { FormPicker };