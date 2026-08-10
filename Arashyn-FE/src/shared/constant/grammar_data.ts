import type {grammar_response} from "@/shared/response/grammar_response.ts";

export const grammar_data: grammar_response = {
    id: "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d",
    title: "～ことにする (ko to ni suru)",
    components: [
        {
            groupKey: 1,
            order: 1,
            keyword: null,
            form: "V-ru / V-nai",
        },
        {
            groupKey: 1,
            order: 2,
            keyword: "ことにする",
            form: null,
        },
        {
            groupKey: 2,
            order: 1,
            keyword: null,
            form: "V-ta",
        },
        {
            groupKey: 2,
            order: 2,
            keyword: "ことにする",
            form: null,
        },
    ],
    meanings: [
        {
            content:
                "Meaning 1: To decide / choose to do (or not do) something based on personal volition.",
        },
        {
            content:
                "Meaning 2: To pretend as if something has happened or has been done (usually used with V-ta / V-te iru).",
        },
    ],
    filters: [
        { name: "N3" },
        { name: "Decision" },
        { name: "Pretense" },
    ],
}

export const grammarList: grammar_response[] = [
    {
        id: "f8e7d6c5-b4a3-2f1e-0d9c-8b7a6f5e4d3c",
        title: "～ものだ (mono da)",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-dict / Adj-i / Adj-na" },
            { groupKey: 1, order: 2, keyword: "ものだ", form: null },
            { groupKey: 2, order: 1, keyword: null, form: "V-ta" },
            { groupKey: 2, order: 2, keyword: "ものだ", form: null },
        ],
        meanings: [
            {
                content:
                    "Meaning 1 (General truth / Social norm): It is natural / normal to...; used to state how things inherently should be.",
            },
            {
                content:
                    "Meaning 2 (Reminiscence): Used to do... in the past (expresses nostalgic memories, paired with V-ta).",
            },
            {
                content:
                    "Meaning 3 (Deep feeling): Expresses strong emotion, surprise, or earnest desire.",
            },
        ],
        filters: [{ name: "N3" }, { name: "N2" }, { name: "Multi-meaning" }],
    },
    {
        id: "c9b8a7f6-e5d4-3c2b-1a0f-9e8d7c6b5a4s",
        title: "～ところだ (tokoro da)",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-dict" },
            { groupKey: 1, order: 2, keyword: "ところだ", form: null },
            { groupKey: 2, order: 1, keyword: null, form: "V-te iru" },
            { groupKey: 2, order: 2, keyword: "ところだ", form: null },
            { groupKey: 3, order: 1, keyword: null, form: "V-ta" },
            { groupKey: 3, order: 2, keyword: "ところだ", form: null },
        ],
        meanings: [
            {
                content:
                    "Meaning 1 (V-dict + ところだ): About to do something / on the verge of doing something.",
            },
            {
                content: "Meaning 2 (V-te iru + ところだ): In the middle of doing something.",
            },
            { content: "Meaning 3 (V-ta + ところだ): Just finished doing something." },
        ],
        filters: [{ name: "N3" }, { name: "Time Aspect" }],
    },
]