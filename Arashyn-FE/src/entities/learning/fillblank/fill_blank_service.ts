import { fillBlankApi } from "@/lib/api/api_client.ts";
import type {
    CreateFillBlankTestRequest,
    SubmitFillBlankRequest,
} from "@/lib/api/generated";

export const fillBlankService = {
    /** POST /learning/fill_blank */
    async create(request: CreateFillBlankTestRequest) {
        const { data } = await fillBlankApi.create8({ createFillBlankTestRequest: request });
        return data;
    },

    /** POST /learning/fill_blank/submit */
    async submit(request: SubmitFillBlankRequest) {
        const { data } = await fillBlankApi.submit({ submitFillBlankRequest: request });
        return data;
    },
};