package com.arashi.edu.arashynbe.features.system.folder.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.system.Deck;
import com.arashi.edu.arashynbe.entity.system.Folder;
import com.arashi.edu.arashynbe.entity.system.support.FolderDeck;
import com.arashi.edu.arashynbe.entity.system.support.FolderHierarchy;
import com.arashi.edu.arashynbe.entity.system.support.FolderHierarchyId;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckListResponse;
import com.arashi.edu.arashynbe.features.system.folder.dto.request.FolderCreateRequest;
import com.arashi.edu.arashynbe.features.system.folder.dto.request.FolderUpdateRequest;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderDetailResponse;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderIdResponse;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderListResponse;
import com.arashi.edu.arashynbe.features.system.folder.service.FolderService;
import com.arashi.edu.arashynbe.repository.hub.UserFolderRepo;
import com.arashi.edu.arashynbe.repository.system.FolderRepo;
import com.arashi.edu.arashynbe.repository.system.support.FolderDeckRepo;
import com.arashi.edu.arashynbe.repository.system.support.FolderHierarchyRepo;
import com.arashi.edu.arashynbe.shared.currentaccount.CurrentAccountProvider;
import com.arashi.edu.arashynbe.shared.enums.Language;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import com.arashi.edu.arashynbe.shared.ownership.OwnerShip;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderServiceImpl implements FolderService {

  private final FolderRepo folderRepo;
  private final FolderDeckRepo folderDeckRepo;
  private final UserFolderRepo userFolderRepo;
  private final FolderHierarchyRepo folderHierarchyRepo;

  private final CurrentAccountProvider currentAccountProvider;

  private final OwnerShip ownerShip;

  @Override
  public FolderIdResponse createFolder(FolderCreateRequest request) {
    Account owner = currentAccountProvider.get();

    Folder folder = new Folder();
    folder.setName(request.name());
    folder.setOwner(owner);
    folder.setIsPublic(Boolean.TRUE.equals(request.isPublic()));

    folder = folderRepo.save(folder);

    if (request.parentId() != null) {
      attachParent(folder, request.parentId());
    }

    return new FolderIdResponse(folder.getId());
  }

  @Override
  public FolderIdResponse updateFolder(FolderUpdateRequest request) {
    Folder folder = ownerShip.requireOwnership(
            request.id(), folderRepo, ErrorCode.FOLDER_NOT_FOUND);

    if (request.name() != null) {
      folder.setName(request.name());
    }
    if (request.isPublic() != null) {
      folder.setIsPublic(request.isPublic());
    }

    changeParent(folder, request.oldParentId(), request.newParentId());

    return new FolderIdResponse(folder.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public FolderListResponse listFolders() {
    List<FolderListResponse.FolderSummariseResponse> folders = folderRepo.findAllByIsPublicTrue()
            .stream()
            .map(this::toSummariseResponse)
            .toList();

    return new FolderListResponse(folders);
  }

  @Override
  @Transactional(readOnly = true)
  public FolderDetailResponse findFolderById(UUID id) {
    Folder folder = folderRepo.findByIdAndOwnerIsNotNull(id)
            .filter(this::canView)
            .orElseThrow(() -> new ApiException(ErrorCode.FOLDER_NOT_FOUND));

    return toDetailResponse(folder);
  }

  @Override
  public boolean hasReferences(UUID id) {
    return userFolderRepo.existsByFolderId(id);
  }

  @Override
  public void deleteFolder(UUID id) {
    Folder folder = ownerShip.requireOwnership(
            id,
            folderRepo,
            ErrorCode.FOLDER_NOT_FOUND
    );

    folderRepo.softDelete(folder.getId());
  }

  private FolderListResponse.FolderSummariseResponse toSummariseResponse(Folder folder) {
    var owner = folder.getOwner();

    return new FolderListResponse.FolderSummariseResponse(
            folder.getId(),
            folder.getName(),
            owner.getId(),
            owner.getUsername()
    );
  }

  private FolderDetailResponse toDetailResponse(Folder folder) {
    UUID userId = CurrentUser.getId();

    Set<DeckListResponse.DeckSummariseResponse> decks = folderDeckRepo
            .findWithDeckByFolderId(folder.getId())
            .stream()
            .map(FolderDeck::getDeck)
            .map(this::toDeckSummary)
            .collect(Collectors.toSet());

    Set<FolderListResponse.FolderSummariseResponse> childFolders = folderRepo
            .findVisibleChildren(folder.getId(), userId)
            .stream()
            .map(this::toSummariseResponse)
            .collect(Collectors.toSet());

    Set<FolderListResponse.FolderSummariseResponse> parentFolders = folderRepo
            .findVisibleParents(folder.getId(), userId)
            .stream()
            .map(this::toSummariseResponse)
            .collect(Collectors.toSet());

    return new FolderDetailResponse(
            folder.getId(),
            folder.getName(),
            folder.getOwner().getId(),
            folder.getIsPublic(),
            decks,
            childFolders,
            parentFolders,
            folder.getCreatedAt(),
            folder.getUpdatedAt()
    );
  }

  private DeckListResponse.DeckSummariseResponse toDeckSummary(Deck deck) {
    return new DeckListResponse.DeckSummariseResponse(
            deck.getId(),
            deck.getName(),
            deck.getDescription(),
            parseLanguage(deck.getLanguage()),
            deck.getOwner() != null ? deck.getOwner().getId() : null
    );
  }

  private Language parseLanguage(String value) {
    try {
      return Language.valueOf(value);
    } catch (IllegalArgumentException | NullPointerException e) {
      return null;
    }
  }

  private void changeParent(Folder folder, UUID oldParentId, UUID newParentId) {
    if (oldParentId == null && newParentId == null) return;
    if (oldParentId != null && oldParentId.equals(newParentId)) return;

    UUID folderId = folder.getId();

    if (newParentId != null
            && (newParentId.equals(folderId)
            || folderHierarchyRepo.isDescendant(folderId, newParentId)
            || folderHierarchyRepo.existsByIdParentIdAndIdChildId(newParentId, folderId))) {
      throw new ApiException(ErrorCode.INVALID_REQUEST);
    }

    if (oldParentId != null
            && folderHierarchyRepo.deleteLink(oldParentId, folderId) == 0) {
      throw new ApiException(ErrorCode.INVALID_REQUEST);
    }

    if (newParentId != null) {
      attachParent(folder, newParentId);
    }
  }

  private void attachParent(Folder folder, UUID parentId) {
    Folder parent = ownerShip.requireOwnership(parentId, folderRepo, ErrorCode.FOLDER_NOT_FOUND);

    FolderHierarchy link = FolderHierarchy.builder()
            .id(new FolderHierarchyId(parent.getId(), folder.getId()))
            .parent(parent)
            .child(folder)
            .build();

    try {
      folderHierarchyRepo.saveAndFlush(link);
    } catch (DataAccessException e) {
      throw new ApiException(ErrorCode.INVALID_REQUEST);
    }
  }

  private boolean canView(Folder folder) {
    return Boolean.TRUE.equals(folder.getIsPublic())
            || folder.getOwner().getId().equals(CurrentUser.getId());
  }
}