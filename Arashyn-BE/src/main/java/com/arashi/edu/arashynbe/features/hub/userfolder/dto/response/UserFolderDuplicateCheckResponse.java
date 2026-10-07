package com.arashi.edu.arashynbe.features.hub.userfolder.dto.response;

import java.util.UUID;

public record UserFolderDuplicateCheckResponse(

        boolean duplicated,

        UUID existingUserFolderId

) {
}