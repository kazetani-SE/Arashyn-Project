import { userFolderApi } from "@/lib/api/api_client.ts";
import type {
    UserFolderCloneRequest,
    UserFolderCreateRequest,
    UserFolderDuplicateCheckRequest,
    UserFolderUpdateRequest,
} from "@/lib/api/generated";

export const userFolderService = {
    /** GET /user_folder */
    async list() {
        const { data } = await userFolderApi.list1();
        return data;
    },

    /** GET /user_folder/root */
    async root() {
        const { data } = await userFolderApi.root();
        return data;
    },

    /** GET /user_folder/:id */
    async getDetail(id: string) {
        const { data } = await userFolderApi.detail1({ id });
        return data;
    },

    /** POST /user_folder */
    async create(request: UserFolderCreateRequest) {
        const { data } = await userFolderApi.create1({ userFolderCreateRequest: request });
        return data;
    },

    /** POST /user_folder/clone */
    async clone(request: UserFolderCloneRequest) {
        const { data } = await userFolderApi.clone({ userFolderCloneRequest: request });
        return data;
    },

    /** POST /user_folder/check_duplicate */
    async checkDuplicate(request: UserFolderDuplicateCheckRequest) {
        const { data } = await userFolderApi.checkDuplicate1({
            userFolderDuplicateCheckRequest: request,
        });
        return data;
    },

    /** PUT /user_folder */
    async update(request: UserFolderUpdateRequest) {
        const { data } = await userFolderApi.update1({ userFolderUpdateRequest: request });
        return data;
    },

    /** DELETE /user_folder/:id */
    async delete(id: string) {
        await userFolderApi.delete1({ id });
    },
};