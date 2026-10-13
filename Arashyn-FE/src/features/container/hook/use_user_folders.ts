import { useQuery } from "@tanstack/react-query";
import {userFolderService} from "@/entities/hub/userfolder/user_folder_service.ts";

export function useUserFolders() {
    return useQuery({
        queryKey: ["user-folders"],
        queryFn: () => userFolderService.list(),
        select: (data) => data.userFolders ?? [],
    });
}