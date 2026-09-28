package com.arashi.edu.arashynbe.features.system.folder.dto.response;

import java.util.List;
import java.util.UUID;

public record FolderListResponse(
        List<FolderSummariseResponse> items
) {
  public record FolderSummariseResponse(

          UUID id,

          String name,

          UUID ownerId,

          String ownerName

  ) {
  }
}