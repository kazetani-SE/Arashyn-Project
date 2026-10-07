package com.arashi.edu.arashynbe.features.hub.userfolder.dto.response;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record UserFolderDetailResponse(

        UUID id,

        String name,

        UUID systemFolderId,

        Set<Children> children,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt
) {

  public record Children(

          UUID id,

          String name,

          Boolean isFolder

  ){}

}