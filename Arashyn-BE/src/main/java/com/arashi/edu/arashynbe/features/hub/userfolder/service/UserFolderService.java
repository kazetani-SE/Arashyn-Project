package com.arashi.edu.arashynbe.features.hub.userfolder.service;

import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderCloneRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderCreateRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderIdResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderListResponse;

import java.util.UUID;

public interface UserFolderService {

  UserFolderIdResponse create(UserFolderCreateRequest request);

  UserFolderIdResponse clone(UserFolderCloneRequest request);

  UserFolderDuplicateCheckResponse checkDuplicate(UserFolderDuplicateCheckRequest request);

  UserFolderIdResponse update(UserFolderUpdateRequest request);

  UserFolderListResponse findAll();

  UserFolderListResponse findRoot();

  UserFolderDetailResponse detailById(UUID id);

  void delete(UUID id);
}