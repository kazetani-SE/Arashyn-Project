import { useQuery } from "@tanstack/react-query";
import { userDeckService } from "@/entities/hub/userdeck/user_deck_service.ts";

export function useGrammarDuplicates(deckId: string, enabled: boolean) {
    return useQuery({
        queryKey: ["user-deck-grammar-duplicates", deckId],
        queryFn: () => userDeckService.checkGrammarDuplicates(deckId),
        select: (data) => data.duplicateCheckResponseList ?? [],
        enabled,
        gcTime: 0, // always re-check when the dialog reopens
    });
}