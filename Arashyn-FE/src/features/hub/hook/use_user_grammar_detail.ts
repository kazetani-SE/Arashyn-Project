import { useQuery } from "@tanstack/react-query";
import {userGrammarService} from "@/entities/hub/usergrammar/user_grammar_service.ts";

export function useUserGrammarDetail(id: string) {
    return useQuery({
        queryKey: ["user-grammar", id],
        queryFn: () => userGrammarService.getDetail(id),
        enabled: !!id,
        staleTime: 5 * 60 * 1000,
        gcTime: 30 * 60 * 1000,
    });
}