import type { grammar_response } from "@/shared/responses/grammar_response.ts";

export const grammar_data: grammar_response = {
    id: "6848c939-ac4d-4bf2-aa77-b6d525c76989",
    title: "～ておく / ～とく (Updated)",
    components: [
        { groupKey: 1, order: 1, keyword: null, form: "V-て" },
        { groupKey: 1, order: 2, keyword: "おく", form: null },
        { groupKey: 2, order: 1, keyword: "とく", form: null },
    ],
    meanings: [
        { content: "To leave something as it is (temporary state/measure)" },
        { content: "To do something in advance (preparatory action)" },
        { content: "To take care of a situation for future convenience" },
        { content: "To do something in advance (Casual speech / Contraction of ~te oku)" },
    ],
    filters: [{ name: "N4" }],
};

export const grammarList: grammar_response[] = [
    grammar_data,
    {
        id: "c5486765-b363-435b-83bb-57d95e1955e1",
        title: "chẳng những...mà còn...",
        components: [
            { groupKey: 1, order: 1, keyword: "chẳng những", form: null },
            { groupKey: 1, order: 2, keyword: "mà còn", form: null },
        ],
        meanings: [{ content: "Biểu thị quan hệ tăng tiến toàn diện." }],
        filters: [{ name: "Bậc 6" }],
    },
    {
        id: "82cd0b84-31c2-4bfc-a3d2-6c3bee9fbade",
        title: "tuy...nhưng...",
        components: [
            { groupKey: 1, order: 1, keyword: "tuy", form: null },
            { groupKey: 1, order: 2, keyword: "nhưng", form: null },
        ],
        meanings: [{ content: "Biểu thị quan hệ nghịch lý, nhượng bộ." }],
        filters: [{ name: "Bậc 5" }],
    },
    {
        id: "e4f26e10-7e98-4c15-aac9-b64a0fe29a03",
        title: "vì...nên...",
        components: [
            { groupKey: 1, order: 1, keyword: "vì", form: null },
            { groupKey: 1, order: 2, keyword: null, form: "Động từ chỉ trạng thái" },
            { groupKey: 1, order: 3, keyword: "nên", form: null },
        ],
        meanings: [{ content: "Biểu thị quan hệ nguyên nhân - kết quả." }],
        filters: [{ name: "Bậc 4" }],
    },
    {
        id: "dd9523a2-6737-4d92-b86e-6a7d2701817c",
        title: "sẽ",
        components: [
            { groupKey: 1, order: 1, keyword: "sẽ", form: null },
            { groupKey: 1, order: 2, keyword: null, form: "Động từ chỉ hành động" },
        ],
        meanings: [{ content: "Hành động sẽ xảy ra trong tương lai." }],
        filters: [{ name: "Bậc 3" }],
    },
    {
        id: "4c11c607-6d8d-428d-af51-ce02e9104ce3",
        title: "đã",
        components: [
            { groupKey: 1, order: 1, keyword: "đã", form: null },
            { groupKey: 1, order: 2, keyword: null, form: "Động từ nguyên thể" },
        ],
        meanings: [{ content: "Hành động đã xảy ra và kết thúc trong quá khứ." }],
        filters: [{ name: "Bậc 2" }],
    },
    {
        id: "9b3bd940-5bc5-45b6-9136-d08b398d98cc",
        title: "đang",
        components: [
            { groupKey: 1, order: 1, keyword: "đang", form: null },
            { groupKey: 1, order: 2, keyword: null, form: "Động từ chỉ hành động" },
        ],
        meanings: [{ content: "Hành động đang diễn ra tại thời điểm nói." }],
        filters: [{ name: "Bậc 1" }],
    },
    {
        id: "903ddb6c-e7ba-4c27-9d61-74e4066f5f17",
        title: "哪怕...也...",
        components: [
            { groupKey: 1, order: 1, keyword: "哪怕", form: null },
            { groupKey: 1, order: 2, keyword: "也", form: null },
        ],
        meanings: [{ content: "Even if... still..." }],
        filters: [{ name: "HSK 6" }],
    },
    {
        id: "bd7945dc-5e16-4a4f-8149-b1aebe2bf005",
        title: "不仅...而且...",
        components: [
            { groupKey: 1, order: 1, keyword: "不仅", form: null },
            { groupKey: 1, order: 2, keyword: "而且", form: null },
        ],
        meanings: [{ content: "Not only... but also." }],
        filters: [{ name: "HSK 5" }],
    },
    {
        id: "16c6496b-935c-4450-ad53-1fc7903f7745",
        title: "虽然...但是...",
        components: [
            { groupKey: 1, order: 1, keyword: "虽然", form: null },
            { groupKey: 1, order: 2, keyword: null, form: "Verb" },
            { groupKey: 1, order: 3, keyword: "但是", form: null },
        ],
        meanings: [{ content: "Although... but..." }],
        filters: [{ name: "HSK 4" }],
    },
    {
        id: "a0b88654-378a-435d-81d8-f591e4da4a14",
        title: "过",
        components: [{ groupKey: 1, order: 1, keyword: null, form: "过" }],
        meanings: [{ content: "Indicates past experience." }],
        filters: [{ name: "HSK 3" }],
    },
    {
        id: "3b6d160b-fe64-4ef9-9984-7595cc3409cc",
        title: "了",
        components: [{ groupKey: 1, order: 1, keyword: null, form: "了" }],
        meanings: [{ content: "Indicates completion of an action." }],
        filters: [{ name: "HSK 2" }],
    },
    {
        id: "ba128fb8-b901-438f-ae9c-2a22ad5381cd",
        title: "正在",
        components: [
            { groupKey: 1, order: 1, keyword: "正在", form: null },
            { groupKey: 1, order: 2, keyword: null, form: "Verb" },
        ],
        meanings: [{ content: "In the process of doing something." }],
        filters: [{ name: "HSK 1" }],
    },
    {
        id: "060e91e1-91b5-4577-b8d6-ea735173e652",
        title: "기 마련이다",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-고" },
            { groupKey: 1, order: 2, keyword: "기 마련이다", form: null },
        ],
        meanings: [{ content: "It is natural or bound to happen." }],
        filters: [{ name: "TOPIK 6" }],
    },
    {
        id: "75285f8d-4a39-40f2-a84d-dd1371a05071",
        title: "(으)ㄹ 뿐만 아니라",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-고" },
            { groupKey: 1, order: 2, keyword: "뿐만 아니라", form: null },
        ],
        meanings: [{ content: "Not only... but also." }],
        filters: [{ name: "TOPIK 5" }],
    },
    {
        id: "17f15d40-e752-4c27-bce0-27b6517e2bed",
        title: "아/어 버리다",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-아/어" },
            { groupKey: 1, order: 2, keyword: "버리다", form: null },
        ],
        meanings: [{ content: "Completely done with a feeling of regret or relief." }],
        filters: [{ name: "TOPIK 4" }],
    },
    {
        id: "c239f832-5d9a-4c4b-bfff-edbcfafe2c99",
        title: "(으)ㄴ 적이 있다",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-(으)ㄴ" },
            { groupKey: 1, order: 2, keyword: "적이 있다", form: null },
        ],
        meanings: [{ content: "Have the experience of doing something." }],
        filters: [{ name: "TOPIK 3" }],
    },
    {
        id: "b8037f81-c834-49c6-873b-24f3cfdc0a5d",
        title: "고 싶다",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-고" },
            { groupKey: 1, order: 2, keyword: "싶다", form: null },
        ],
        meanings: [{ content: "Want to do something." }],
        filters: [{ name: "TOPIK 2" }],
    },
    {
        id: "a759fa92-23c1-4ce8-b9b6-b6dbffe79236",
        title: "아/어 주세요",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-아/어" },
            { groupKey: 1, order: 2, keyword: "주세요", form: null },
        ],
        meanings: [{ content: "Please do something for me." }],
        filters: [{ name: "TOPIK 1" }],
    },
    {
        id: "82d993bd-4084-436c-b7be-81b506a1a101",
        title: "ています",
        components: [
            { groupKey: 1, order: 1, keyword: null, form: "V-て" },
            { groupKey: 1, order: 2, keyword: "います", form: null },
        ],
        meanings: [{ content: "Expresses an action currently in progress." }],
        filters: [{ name: "N5" }],
    },
];