import {FeatureBackground} from "@/components/background/FeatureBackground.tsx";
import {useSetBreadcrumb} from "@/layout/topbar/hooks/useSetBreadcrumb.ts";
import {ROUTE_PATHS, ROUTES} from "@/app/router/route.ts";
import {useParams} from "react-router-dom";
import {type ApiResponse} from "@/features/detail/parts/TestPart.tsx";
import {ContentPart} from "@/features/detail/parts/ContentPart.tsx";
import type {grammar_detail_response} from "@/shared/responses/grammar_detail_response.ts";
import {SummarizePart} from "@/features/detail/parts/SummarizePart.tsx";

const mockResponse: ApiResponse<grammar_detail_response> = {
    data: {
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
    message: "Success",
    status: 200,
};

export default function DetailPage() {

    const { grammarId } = useParams<{ grammarId: string }>();
    useSetBreadcrumb("Detail", grammarId ? ROUTES.grammarDetail(grammarId) : ROUTE_PATHS.DETAIL);

    return (
        <div>
            <FeatureBackground />

            <div className="mx-auto grid max-w-5xl grid-cols-1 gap-10 px-6 py-10 md:grid-cols-[220px_1fr]">
                <SummarizePart data={mockResponse.data} />

                <ContentPart data={mockResponse.data} />
            </div>
        </div>
    )
}