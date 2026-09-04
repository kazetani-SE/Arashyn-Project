import type {
    grammar_response,
    grammar_detail_response,
} from "@/entities/grammar/grammar_types.ts";

export const grammar_detail_data: grammar_detail_response[] = [
    {
        id: "grammar-001",
        title: "～てはいけない",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-001-c1",
                        order: 1,
                        keyword: null,
                        form: "V-て",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-001-c2",
                        order: 2,
                        keyword: "はいけない",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-001-m1",
                        content: "Không được làm gì.",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-001-e1",
                                sentence: "ここで写真を撮ってはいけません。",
                                translation: "Không được chụp ảnh ở đây.",
                                note: "Cách nói lịch sự.",
                            },
                            {
                                id: "grammar-001-e2",
                                sentence: "ここに入ってはいけない。",
                                translation: "Không được vào đây.",
                                note: "Cách nói thông thường.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-001-n1",
                content: "Dùng để diễn tả sự cấm đoán.",
            },
        ],

        filters: [
            {
                id: "n4",
                name: "N4",
            },
        ],
    },

    {
        id: "grammar-002",
        title: "～ておく",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-002-c1",
                        order: 1,
                        keyword: null,
                        form: "V-て",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-002-c2",
                        order: 2,
                        keyword: "おく",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-002-m1",
                        content: "Làm trước một việc để chuẩn bị.",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-002-e1",
                                sentence: "旅行の前にホテルを予約しておきます。",
                                translation: "Tôi sẽ đặt khách sạn trước chuyến đi.",
                                note: "Hành động chuẩn bị trước.",
                            },
                        ],
                    },
                    {
                        id: "grammar-002-m2",
                        content: "Để nguyên một trạng thái.",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-002-e2",
                                sentence: "そのままにしておいてください。",
                                translation: "Hãy cứ để nguyên như vậy.",
                                note: "Giữ nguyên trạng thái.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-002-n1",
                content: "Trong hội thoại, ～ておく thường rút gọn thành ～とく.",
            },
        ],

        filters: [
            {
                id: "n4",
                name: "N4",
            },
        ],
    },

    {
        id: "grammar-003",
        title: "～たい",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-003-c1",
                        order: 1,
                        keyword: null,
                        form: "V-ます",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-003-c2",
                        order: 2,
                        keyword: "たい",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-003-m1",
                        content: "Muốn làm gì.",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-003-e1",
                                sentence: "日本へ行きたいです。",
                                translation: "Tôi muốn đi Nhật.",
                                note: "Diễn tả mong muốn của người nói.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-003-n1",
                content: "Thường dùng để nói về mong muốn của bản thân.",
            },
        ],

        filters: [
            {
                id: "n5",
                name: "N5",
            },
        ],
    },

    {
        id: "grammar-004",
        title: "～なければならない",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-004-c1",
                        order: 1,
                        keyword: null,
                        form: "V-なければ",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-004-c2",
                        order: 2,
                        keyword: "ならない",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-004-m1",
                        content: "Phải làm gì.",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-004-e1",
                                sentence: "明日早く起きなければなりません。",
                                translation: "Ngày mai tôi phải dậy sớm.",
                                note: "Diễn tả nghĩa vụ.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-004-n1",
                content: "Dùng khi người nói bắt buộc phải thực hiện một việc.",
            },
        ],

        filters: [
            {
                id: "n4",
                name: "N4",
            },
        ],
    },

    {
        id: "grammar-005",
        title: "～と思います",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-005-c1",
                        order: 1,
                        keyword: null,
                        form: "Plain form",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-005-c2",
                        order: 2,
                        keyword: "と思います",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-005-m1",
                        content: "Tôi nghĩ rằng...",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-005-e1",
                                sentence: "明日は雨だと思います。",
                                translation: "Tôi nghĩ ngày mai trời sẽ mưa.",
                                note: "Diễn tả suy nghĩ hoặc ý kiến.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-005-n1",
                content: "Dùng để diễn tả ý kiến hoặc suy đoán.",
            },
        ],

        filters: [
            {
                id: "n4",
                name: "N4",
            },
        ],
    },

    {
        id: "grammar-006",
        title: "～たことがある",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-006-c1",
                        order: 1,
                        keyword: null,
                        form: "V-た",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-006-c2",
                        order: 2,
                        keyword: "ことがある",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-006-m1",
                        content: "Đã từng làm gì.",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-006-e1",
                                sentence: "日本に行ったことがあります。",
                                translation: "Tôi đã từng đi Nhật.",
                                note: "Nói về kinh nghiệm trong quá khứ.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-006-n1",
                content: "Dùng để nói về một trải nghiệm đã từng xảy ra.",
            },
        ],

        filters: [
            {
                id: "n4",
                name: "N4",
            },
        ],
    },

    {
        id: "grammar-007",
        title: "～ながら",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-007-c1",
                        order: 1,
                        keyword: null,
                        form: "V-ます",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-007-c2",
                        order: 2,
                        keyword: "ながら",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-007-m1",
                        content: "Vừa làm A vừa làm B.",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-007-e1",
                                sentence: "音楽を聞きながら勉強します。",
                                translation: "Tôi vừa nghe nhạc vừa học.",
                                note: "Hai hành động xảy ra cùng lúc.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-007-n1",
                content: "Chủ thể của hai hành động thường giống nhau.",
            },
        ],

        filters: [
            {
                id: "n3",
                name: "N3",
            },
        ],
    },

    {
        id: "grammar-008",
        title: "～そうです",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-008-c1",
                        order: 1,
                        keyword: null,
                        form: "V-ます / Adj",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-008-c2",
                        order: 2,
                        keyword: "そうです",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-008-m1",
                        content: "Có vẻ như...",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-008-e1",
                                sentence: "このケーキはおいしそうです。",
                                translation: "Cái bánh này trông có vẻ ngon.",
                                note: "Suy đoán dựa trên vẻ ngoài.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-008-n1",
                content: "Dùng khi đưa ra nhận xét dựa trên những gì quan sát được.",
            },
        ],

        filters: [
            {
                id: "n4",
                name: "N4",
            },
        ],
    },

    {
        id: "grammar-009",
        title: "～たら",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-009-c1",
                        order: 1,
                        keyword: null,
                        form: "V-た",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-009-c2",
                        order: 2,
                        keyword: "ら",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-009-m1",
                        content: "Nếu / khi...",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-009-e1",
                                sentence: "時間があったら、映画を見ます。",
                                translation: "Nếu có thời gian, tôi sẽ xem phim.",
                                note: "Diễn tả điều kiện.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-009-n1",
                content: "Có thể dùng cho điều kiện hoặc thời điểm trong tương lai.",
            },
        ],

        filters: [
            {
                id: "n4",
                name: "N4",
            },
        ],
    },

    {
        id: "grammar-010",
        title: "～ので",
        language: "JA",
        isPublic: true,
        ownerId: "mock-owner",
        ownerName: "Mock User",

        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "grammar-010-c1",
                        order: 1,
                        keyword: null,
                        form: "Plain form",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "grammar-010-c2",
                        order: 2,
                        keyword: "ので",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "grammar-010-m1",
                        content: "Vì / bởi vì...",
                        groupKey: 1,
                        examples: [
                            {
                                id: "grammar-010-e1",
                                sentence: "雨なので、出かけません。",
                                translation: "Vì trời mưa nên tôi không ra ngoài.",
                                note: "Diễn tả nguyên nhân.",
                            },
                        ],
                    },
                ],
            },
        ],

        notes: [
            {
                id: "grammar-010-n1",
                content: "Cách diễn đạt nguyên nhân tương đối mềm và tự nhiên.",
            },
        ],

        filters: [
            {
                id: "n4",
                name: "N4",
            },
        ],
    },
];

/**
 * Convert detail → list response.
 */
function toGrammarSummary(
    detail: grammar_detail_response,
): grammar_response {
    return {
        id: detail.id,
        title: detail.title,

        components: detail.groups.flatMap((group) =>
            group.components.map((component) => ({
                groupKey: component.groupKey,
                order: component.order,
                keyword: component.keyword,
                form: component.form,
            })),
        ),

        meanings: detail.groups.flatMap((group) =>
            group.meanings.map((meaning) => ({
                content: meaning.content,
            })),
        ),

        filters: detail.filters.map((filter) => ({
            name: filter.name,
        })),
    };
}

/**
 * List data is generated from the exact same detail data,
 * so every item in the list is guaranteed to have a detail.
 */
export const grammar_list_data: grammar_response[] =
    grammar_detail_data.map(toGrammarSummary);

export const grammar_mock_data: grammar_response =
    grammar_list_data[0];

