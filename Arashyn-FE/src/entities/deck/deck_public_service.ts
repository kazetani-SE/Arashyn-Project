import { deckPublicApi } from "@/lib/api/api_client.ts";

export const deckPublicService = {
    /** GET /public/deck */
    async list() {
        const { data } = await deckPublicApi.getDeckList();
        return data;
    },

    /** GET /public/deck/:deckId */
    async getDetail(id: string) {
        const { data } = await deckPublicApi.getDeck({ deckId: id });
        return data;
    },
};