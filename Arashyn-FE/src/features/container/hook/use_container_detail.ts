import { useQuery } from "@tanstack/react-query";
import { deckPublicService } from "@/entities/system/deck/deck_public_service.ts";
import { folderPublicService } from "@/entities/system/folder/folder_public_service.ts";

type UseContainerDetailParams = {
    type: "deck" | "folder";
    id: string;
};

export function useContainerDetail({ type, id }: UseContainerDetailParams) {
    return useQuery({
        queryKey: ["container-detail", type, id],
        queryFn: () =>
            type === "deck"
                ? deckPublicService.getDetail(id)
                : folderPublicService.getDetail(id),
        enabled: !!id,
        staleTime: 5 * 60 * 1000,
        gcTime: 30 * 60 * 1000,
    });
}