import { useQuery } from "@tanstack/react-query";
import {userFolderService} from "@/entities/hub/userfolder/user_folder_service.ts";
import {userDeckService} from "@/entities/hub/userdeck/user_deck_service.ts";

export function useHubRoot() {
    const foldersQuery = useQuery({
        queryKey: ["user-folder", "root"],
        queryFn: () => userFolderService.root(),
    });

    const decksQuery = useQuery({
        queryKey: ["user-deck", "root"],
        queryFn: () => userDeckService.root(),
    });

    return {
        folders: foldersQuery.data?.userFolders ?? [],
        decks: decksQuery.data?.userDecks ?? [],
        isLoading: foldersQuery.isLoading || decksQuery.isLoading,
        isError: foldersQuery.isError || decksQuery.isError,
    };
}