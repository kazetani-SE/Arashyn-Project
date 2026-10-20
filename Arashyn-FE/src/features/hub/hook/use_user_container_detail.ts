import { useQuery } from "@tanstack/react-query";
import {userDeckService} from "@/entities/hub/userdeck/user_deck_service.ts";
import {userFolderService} from "@/entities/hub/userfolder/user_folder_service.ts";

type UseUserContainerDetailParams = {
    type: "deck" | "folder";
    id: string;
};

export function useUserContainerDetail({ type, id }: UseUserContainerDetailParams) {
    return useQuery({
        queryKey: ["user-container", type, id],
        queryFn: async () => {
            if (type === "deck") {
                return userDeckService.getDetail(id);
            }
            return userFolderService.getDetail(id);
        },
        enabled: !!id,
        staleTime: 5 * 60 * 1000,
        gcTime: 30 * 60 * 1000,
    });
}