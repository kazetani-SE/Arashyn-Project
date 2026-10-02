package com.arashi.edu.arashynbe.features.system.folder.service;

import com.arashi.edu.arashynbe.features.system.folder.dto.request.FolderCreateRequest;
import com.arashi.edu.arashynbe.features.system.folder.dto.request.FolderUpdateRequest;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderCheckUpdateResponse;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderDetailResponse;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderIdResponse;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderListResponse;

import java.util.UUID;

public interface FolderService {

  FolderIdResponse createFolder(FolderCreateRequest request);

  FolderIdResponse updateFolder(FolderUpdateRequest request);

  FolderListResponse listFolders();

  FolderDetailResponse findFolderById(UUID id);

  boolean hasReferences(UUID id);

  void deleteFolder(UUID id);

  FolderCheckUpdateResponse checkFolderUpdate(UUID userFolderId);
}