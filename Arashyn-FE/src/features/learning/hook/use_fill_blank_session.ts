import { useMemo, useState } from "react";
import { useMutation } from "@tanstack/react-query";
import type { FillBlankSetting } from "@/features/learning/components/FillBlankSettingDialog.tsx";
import type {
    AnswerResult,
    QuestionComponent,
    SubmitAnswer,
    SubmitFillBlankResponse,
} from "@/lib/api/generated";
import {fillBlankService} from "@/entities/learning/fillblank/fill_blank_service.ts";

type Stage = "setting" | "doing" | "result";

export type QuestionView = {
    meaning?: string;
    components: QuestionComponent[];
};

const toSubmitAnswer = (blank: QuestionComponent, userAnswer: string): SubmitAnswer => ({
    iv: blank.iv ?? "",
    answerToken: blank.answerToken ?? "",
    userAnswer,
});

export function useFillBlankSession(deckId: string) {
    const [stage, setStage] = useState<Stage>("setting");
    const [questions, setQuestions] = useState<QuestionView[]>([]);
    const [answers, setAnswers] = useState<Record<string, string>>({}); // key = answerToken
    const [result, setResult] = useState<SubmitFillBlankResponse | null>(null);

    const createMut = useMutation({
        mutationFn: (setting: FillBlankSetting) =>
            fillBlankService.create({ ...setting, userDeckId: deckId }),
        onSuccess: (data) => {
            const views: QuestionView[] = (data.questions ?? []).map((q) => ({
                meaning: q.meaning,
                components: [...(q.questionComponents ?? [])].sort(
                    (a, b) => (a.order ?? 0) - (b.order ?? 0)
                ),
            }));
            setQuestions(views);
            setAnswers({});
            setResult(null);
            setStage("doing");
        },
    });

    const submitMut = useMutation({
        mutationFn: (payload: SubmitAnswer[]) => fillBlankService.submit({ answers: payload }),
        onSuccess: (data) => {
            setResult(data);
            setStage("result");
        },
    });

    // Tất cả chỗ trống theo thứ tự: câu 1 -> câu cuối, trái -> phải
    const blanks = useMemo(
        () => questions.flatMap((q) => q.components.filter((c) => !!c.answerToken)),
        [questions]
    );

    // results[i] tương ứng blanks[i]
    const resultByToken = useMemo(() => {
        const map = new Map<string, AnswerResult>();
        blanks.forEach((b, i) => {
            const r = result?.results?.[i];
            if (r && b.answerToken) map.set(b.answerToken, r);
        });
        return map;
    }, [blanks, result]);

    const answeredCount = blanks.filter((b) => answers[b.answerToken!]?.trim()).length;

    return {
        stage,
        questions,
        answers,
        result,
        resultByToken,
        totalBlanks: blanks.length,
        answeredCount,
        allAnswered: blanks.length > 0 && answeredCount === blanks.length,

        isCreating: createMut.isPending,
        createError: createMut.isError,
        isSubmitting: submitMut.isPending,
        submitError: submitMut.isError,

        create: (setting: FillBlankSetting) => createMut.mutate(setting),
        setAnswer: (token: string, value: string) =>
            setAnswers((prev) => ({ ...prev, [token]: value })),
        submit: () =>
            submitMut.mutate(blanks.map((b) => toSubmitAnswer(b, answers[b.answerToken!] ?? ""))),
        restart: () => {
            createMut.reset();
            submitMut.reset();
            setQuestions([]);
            setAnswers({});
            setResult(null);
            setStage("setting");
        },
    };
}