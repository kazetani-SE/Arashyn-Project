import { arrangeApi } from "@/lib/api/api_client.ts";
import type {
    CreateArrangeRequest,
    SubmitArrangeRequest,
} from "@/lib/api/generated";

export const arrangeService = {
    /** POST /learning/arrange */
    async create(request: CreateArrangeRequest) {
        const { data } = await arrangeApi.create9({ createArrangeRequest: request });
        return data;
    },

    /** POST /learning/arrange/submit */
    async submit(request: SubmitArrangeRequest) {
        const { data } = await arrangeApi.submit1({ submitArrangeRequest: request });
        return data;
    },
};