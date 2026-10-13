import { folderPublicApi } from "@/lib/api/api_client.ts";

export const folderPublicService = {
    /** GET /public/folder */
    async list() {
        const { data } = await folderPublicApi.getFolderList();
        return data;
    },

    /** GET /public/folder/:id */
    async getDetail(id: string) {
        const { data } = await folderPublicApi.getFolder({ id });
        return data;
    },
};