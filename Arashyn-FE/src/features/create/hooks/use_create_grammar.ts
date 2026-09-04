import * as React from "react"
import { useNavigate } from "react-router-dom"
import { grammarService } from "@/entities/grammar/grammar_service.ts"
import {
    type GrammarFormValues,
    type GroupFormValue,
    type NoteFormValue,
    emptyGrammarFormValues,
    emptyGroup,
} from "@/entities/grammar/grammar_form_types.ts"
import {ROUTES} from "@/app/router/route.ts"
import {Language} from "@/shared/enum/language.ts";

export function useCreateGrammar(language: Language) {
    const [values, setValues] = React.useState<GrammarFormValues>(emptyGrammarFormValues())
    const [submitting, setSubmitting] = React.useState(false)
    const [error, setError] = React.useState<string | null>(null)

    const navigate = useNavigate()

    const updateTitle = (title: string) =>
        setValues((prev) => ({ ...prev, title }))

    const updateGroup = (index: number, next: GroupFormValue) => {
        setValues((prev) => {
            const groups = [...prev.groups]
            groups[index] = next
            return { ...prev, groups }
        })
    }
    const addGroup = () =>
        setValues((prev) => ({ ...prev, groups: [...prev.groups, emptyGroup(prev.groups.length + 1)] }))
    const removeGroup = (index: number) =>
        setValues((prev) => ({ ...prev, groups: prev.groups.filter((_, i) => i !== index) }))

    const updateNote = (index: number, next: NoteFormValue) => {
        setValues((prev) => {
            const notes = [...prev.notes]
            notes[index] = next
            return { ...prev, notes }
        })
    }
    const addNote = () =>
        setValues((prev) => ({
            ...prev,
            notes: [...prev.notes, { content: "", isPublic: true, groupKey: prev.groups[0]?.groupKey ?? 1 }],
        }))
    const removeNote = (index: number) =>
        setValues((prev) => ({ ...prev, notes: prev.notes.filter((_, i) => i !== index) }))

    const updateFilterIds = (filterIds: string[]) =>
        setValues((prev) => ({ ...prev, filterIds }))

    const reset = () => setValues(emptyGrammarFormValues())

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        setError(null)

        try {
            setSubmitting(true)
            const res = await grammarService.create(values)
            reset()
            navigate(ROUTES.grammarDetail(res.id))
        } catch (err) {
            console.error("[CREATE_GRAMMAR_ERROR]", err)
            setError("Failed to create grammar. Please try again.")
        } finally {
            setSubmitting(false)
        }
    }

    return {
        values,
        language,
        submitting,
        error,
        updateTitle,
        updateGroup,
        addGroup,
        removeGroup,
        updateNote,
        addNote,
        removeNote,
        updateFilterIds,
        handleSubmit,
    }
}