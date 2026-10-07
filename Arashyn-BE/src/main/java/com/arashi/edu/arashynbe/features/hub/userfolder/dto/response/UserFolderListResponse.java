package com.arashi.edu.arashynbe.features.hub.userfolder.dto.response;

import java.util.List;
import java.util.UUID;

public record UserFolderListResponse(

        List<UserFolderSummariseResponse> userFolders

) {

  public record UserFolderSummariseResponse(

          UUID id,

          String name

  ){}

}