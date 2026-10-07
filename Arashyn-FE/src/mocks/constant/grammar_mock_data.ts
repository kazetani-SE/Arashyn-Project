import {
    type GrammarDetailResponse,
    type GrammarSummaryResponse,
    GrammarDetailResponseLanguageEnum,
} from "@/lib/api/generated";

export const grammar_list_data: GrammarSummaryResponse[] = [
    {
        id: "grammar-001",
        title: "～てはいけない",
        components: [{ groupKey: 1, order: 1, form: "V-て" }, { groupKey: 1, order: 2, keyword: "はいけない" }],
        meanings: [{ groupKey: 1, content: "Không được làm gì." }],
        filters: [{ id: "n4", name: "N4" }],
    },
];

export const grammar_detail_data: GrammarDetailResponse[] = [
    {
        id: "grammar-001",
        title: "～てはいけない",
        language: GrammarDetailResponseLanguageEnum.Ja,
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",
        groups: [
            {
                groupKey: 1,
                components: [
                    { order: 1, formId: "V-て", optional: false },
                    { order: 2, keyword: "はいけない", optional: false },
                ],
                meanings: [
                    {
                        content: "Không được làm gì.",
                        isPublic: true,
                        examples: [
                            {
                                sentence: "ここで写真を撮ってはいけません。",
                                translation: "Không được chụp ảnh ở đây.",
                                note: "Cách nói lịch sự.",
                                isPublic: true,
                            },
                        ],
                    },
                ],
            },
        ],
        notes: [{ id: "grammar-001-n1", content: "Dùng để diễn tả sự cấm đoán." }],
        filters: [{ id: "n4", name: "N4" }],
    },
];