import type {grammar_detail_response} from "@/shared/responses/grammar_detail_response.ts";

export const detail_grammar_data: grammar_detail_response[] = [
    {
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
    },
    {
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
    },
    {
        id: "6848c939-ac4d-4bf2-aa77-b6d525c76989",
        title: "～ておく / ～とく (Updated)",
        language: "JA",
        isPublic: true,
        ownerId: "7a271742-0d02-4c8c-b841-3a03d6ddab90",
        ownerName: "User01",
        groups: [
            {
                groupKey: 1,
                components: [
                    {
                        id: "fec787ac-b5e0-4871-9d0d-cd80b20b0701",
                        order: 1,
                        keyword: null,
                        form: "V-て",
                        groupKey: 1,
                        optional: false,
                    },
                    {
                        id: "67323999-7ecd-4931-a5ef-f64c47d0533e",
                        order: 2,
                        keyword: "おく",
                        form: null,
                        groupKey: 1,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "855418db-5c95-4407-bbac-85149225894a",
                        content: "To do something in advance (preparatory action)",
                        groupKey: 1,
                        examples: [],
                    },
                    {
                        id: "bb61956d-f2ba-437c-a75f-3ee9240b6acf",
                        content: "To leave something as it is (temporary state/measure)",
                        groupKey: 1,
                        examples: [
                            {
                                id: "54f51464-1faf-4787-adab-663e95aca629",
                                sentence: "test example 1",
                                translation: "string",
                                note: "string",
                            },
                            {
                                id: "1b1ec462-59ed-40d2-ae4f-354b6554147b",
                                sentence: "test example 1",
                                translation: "string",
                                note: "string",
                            },
                            {
                                id: "e5615ff2-af70-473d-a777-4741751ad82d",
                                sentence: "test example 1",
                                translation: "string",
                                note: "string",
                            },
                        ],
                    },
                    {
                        id: "09909750-b374-4627-8f63-e5df2a262f70",
                        content: "To take care of a situation for future convenience",
                        groupKey: 1,
                        examples: [],
                    },
                ],
            },
            {
                groupKey: 2,
                components: [
                    {
                        id: "b6a820c3-0965-4ca8-9f67-b47e702cdd7b",
                        order: 1,
                        keyword: "とく",
                        form: null,
                        groupKey: 2,
                        optional: false,
                    },
                ],
                meanings: [
                    {
                        id: "2a937d2e-7d2c-4433-ac58-d1fa37b99d0b",
                        content: "To do something in advance (Casual speech / Contraction of ~te oku)",
                        groupKey: 2,
                        examples: [],
                    },
                ],
            },
        ],
        notes: [
            {
                "id": "note-101",
                "content": "In casual speech, ～ておく shortens to ～とく (e.g., 買っておく → 買っとく). If the verb ends with ～で (like 飲む → 飲んで), it shortens to ～どく (e.g., 飲んどく)."
            },
            {
                "id": "note-102",
                "content": "Often used with time expressions like あらかじめ (in advance), 事前に (beforehand), or まだ (still)."
            }
        ],
        filters: [
            {
                id: "filter-n4",
                name: "N4",
            },
        ],
    },
]