package com.arashi.edu.arashynbe.features.system.folder.dto.response;

import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckListResponse;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record FolderDetailResponse(

        UUID id,

        String name,

        UUID ownerId,

        String ownerName,

        Boolean isPublic,

        Set<DeckListResponse.DeckSummariseResponse> decks,

        Set<FolderListResponse.FolderSummariseResponse> childFolders,

        Set<FolderListResponse.FolderSummariseResponse> parentFolders,
        
        OffsetDateTime createdAt,

        OffsetDateTime updatedAt
) {
}