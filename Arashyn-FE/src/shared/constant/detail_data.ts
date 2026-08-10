import type {detail_response} from "@/shared/response/detail_response.ts";

export const detail_data: detail_response = {
    id: "e41d5cd4-a1d5-4b12-b926-551c52182f10",
    title: "～てはいけない / ～ちゃいけない",
    language: "JA",
    isPublic: true,
    ownerId: "7a271742-0d02-4c8c-b841-3a03d6ddab90",
    ownerName: "User01",
    groups: [
        {
            groupKey: 1,
            components: [
                {
                    id: "4964add2-8edd-434e-9a5a-a28f7e3eb25d",
                    order: 1,
                    keyword: null,
                    form: "V-て",
                    groupKey: 1,
                    optional: false
                },
                {
                    id: "68f47a64-d711-4a79-a872-59b6cfa0479f",
                    order: 2,
                    keyword: "はいけない",
                    form: null,
                    groupKey: 1,
                    optional: false
                }
            ],
            meanings: [
                {
                    id: "1b11c3eb-0297-4fef-bb5e-1fe0924afeef",
                    content: "Indicates prohibition; not allowed to perform a certain action.",
                    groupKey: 1,
                    examples: [
                        {
                            id: "eaa0d00d-0240-478d-8651-45864b3317f3",
                            sentence: "ここに車を止めてはいけません。",
                            translation: "You must not park your car here.",
                            note: "Prohibition based on regulations, polite form."
                        }
                    ]
                }
            ]
        },
        {
            groupKey: 2,
            components: [
                {
                    id: "e067bf82-db73-45ff-bbe1-a70bdea29e05",
                    order: 1,
                    keyword: "ちゃいけない",
                    form: null,
                    groupKey: 2,
                    optional: false
                }
            ],
            meanings: [
                {
                    id: "e611f49b-5a7e-4e4a-bab8-8cfc8fda7ae6",
                    content: "Casual/shortened spoken form of ~te wa ikenai.",
                    groupKey: 2,
                    examples: [
                        {
                            id: "f3e7139c-d7f9-4205-83c6-e6a5980a4b54",
                            sentence: "まだ入っちゃいけないよ。",
                            translation: "You must not enter yet.",
                            note: "cha ikenai = te wa ikenai"
                        }
                    ]
                }
            ]
        }
    ],
    notes: [
        {
            id: "3a154d73-40ed-450a-ace5-d0de6a057c00",
            content: "If the Te-form of the verb ends in ~de (e.g., yonde), the formal prohibition changes to ~de wa ikenai, and the casual spoken form contracts to ~ja ikenai (e.g., yonja ikenai)."
        }
    ],
    filters: []
};