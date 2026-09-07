package com.arashi.edu.arashynbe.features.system.folder.dto.response;

import java.util.UUID;

public record FolderSummaryResponse(

        UUID id,

        String name,

        String ownerName

) {
}