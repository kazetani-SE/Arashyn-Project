package com.arashi.edu.arashynbe.features.hub.userfolder.service.impl;

import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.hub.UserDeck;
import com.arashi.edu.arashynbe.entity.hub.UserFolder;
import com.arashi.edu.arashynbe.entity.system.Deck;
import com.arashi.edu.arashynbe.entity.system.Folder;
import com.arashi.edu.arashynbe.entity.system.Grammar;
import com.arashi.edu.arashynbe.entity.system.support.DeckGrammar;
import com.arashi.edu.arashynbe.entity.system.support.FolderDeck;
import com.arashi.edu.arashynbe.entity.system.support.FolderHierarchy;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckCloneRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckIdResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.service.UserDeckService;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderCloneRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderCreateRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderIdResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderListResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.service.UserFolderService;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarCreateMultipleRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarCreateRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.service.UserGrammarService;
import com.arashi.edu.arashynbe.features.system.folder.dto.request.FolderCreateRequest;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderIdResponse;
import com.arashi.edu.arashynbe.features.system.folder.service.FolderService;
import com.arashi.edu.arashynbe.repository.hub.UserDeckRepo;
import com.arashi.edu.arashynbe.repository.hub.UserFolderRepo;
import com.arashi.edu.arashynbe.repository.hub.UserGrammarRepo;
import com.arashi.edu.arashynbe.repository.system.DeckRepo;
import com.arashi.edu.arashynbe.repository.system.FolderRepo;
import com.arashi.edu.arashynbe.repository.system.support.DeckGrammarRepo;
import com.arashi.edu.arashynbe.repository.system.support.FolderDeckRepo;
import com.arashi.edu.arashynbe.shared.currentaccount.CurrentAccountProvider;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserFolderServiceImpl implements UserFolderService {

  private final FolderRepo folderRepo;
  private final UserFolderRepo userFolderRepo;
  private final UserDeckRepo userDeckRepo;
  private final UserGrammarRepo userGrammarRepo;
  private final DeckRepo deckRepo;
  private final FolderDeckRepo folderDeckRepo;
  private final DeckGrammarRepo deckGrammarRepo;

  private final FolderService folderService;
  private final UserDeckService userDeckService;
  private final UserGrammarService userGrammarService;

  private final CurrentAccountProvider currentAccountProvider;

  @Override
  public UserFolderIdResponse create(UserFolderCreateRequest request) {
    var user = currentAccountProvider.get();

    UserFolder parent = null;
    UUID systemParentId = null;

    if (request.userFolderId() != null) {
      parent = userFolderRepo
              .findByIdAndUserId(request.userFolderId(), user.getId())
              .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));

      if (parent.getFolderId() != null) {
        systemParentId = folderRepo.findById(parent.getFolderId())
                .filter(sf -> sf.getOwner() != null && sf.getOwner().getId().equals(user.getId()))
                .map(Folder::getId)
                .orElse(null);
      }
    }

    UUID systemFolderId = null;

    if (Boolean.TRUE.equals(request.isPublic())) {
      FolderIdResponse folderIdResponse = folderService.createFolder(new FolderCreateRequest(
              request.name().trim(),
              true,
              systemParentId
      ));

      systemFolderId = folderIdResponse.id();
    }

    UserFolder userFolder = UserFolder.builder()
            .user(user)
            .name(request.name().trim())
            .folderId(systemFolderId)
            .build();

    UserFolder saved = userFolderRepo.save(userFolder);

    if (parent != null) {
      parent.getChildren().add(saved);
    }

    return new UserFolderIdResponse(saved.getId());
  }

  @Override
  public UserFolderIdResponse clone(UserFolderCloneRequest request) {
    Account user = currentAccountProvider.get();

    UserFolder parent = null;

    if (request.userParentFolderId() != null) {
      parent = userFolderRepo
              .findByIdAndUserId(request.userParentFolderId(), user.getId())
              .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));
    }

    Folder folder = folderRepo.findById(request.folderId())
            .orElseThrow(() -> new ApiException(ErrorCode.FOLDER_NOT_FOUND));

    String nameOverride = request.name() != null && !request.name().isBlank()
            ? request.name().trim()
            : null;

    UserFolder result = cloneFolderTree(user, folder, nameOverride, parent);

    return new UserFolderIdResponse(result.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public UserFolderDuplicateCheckResponse checkDuplicate(UserFolderDuplicateCheckRequest request) {
    Account user = currentAccountProvider.get();

    return userFolderRepo
            .findFirstByFolderIdAndUserId(request.id(), user.getId())
            .map(userFolder -> new UserFolderDuplicateCheckResponse(true, userFolder.getId()))
            .orElseGet(() -> new UserFolderDuplicateCheckResponse(false, null));
  }

  @Override
  public UserFolderIdResponse update(UserFolderUpdateRequest request) {
    Account user = currentAccountProvider.get();

    UserFolder userFolder = userFolderRepo.findByIdAndUserId(request.userFolderId(), user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));

    if (request.name() != null && !request.name().isBlank()) {
      userFolder.setName(request.name().trim());
    }

    UUID currentParentId = request.currentParentFolderId();
    UUID newParentId = request.newParentFolderId();

    if (currentParentId == null && newParentId == null) {
      return new UserFolderIdResponse(userFolder.getId());
    }

    if (currentParentId != null && currentParentId.equals(newParentId)) {
      return new UserFolderIdResponse(userFolder.getId());
    }

    if (currentParentId == null) {
      UserFolder newParent = userFolderRepo.findByIdAndUserId(newParentId, user.getId())
              .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));

      linkChild(newParent, userFolder);

      return new UserFolderIdResponse(userFolder.getId());
    }

    UserFolder currentParent = userFolderRepo.findByIdAndUserId(currentParentId, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));

    boolean linked = currentParent.getChildren().stream()
            .anyMatch(c -> c.getId().equals(userFolder.getId()));

    if (!linked) {
      throw new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND);
    }

    if (newParentId == null) {
      currentParent.getChildren().removeIf(c -> c.getId().equals(userFolder.getId()));

      return new UserFolderIdResponse(userFolder.getId());
    }

    UserFolder newParent = userFolderRepo.findByIdAndUserId(newParentId, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));

    linkChild(newParent, userFolder);

    currentParent.getChildren().removeIf(c -> c.getId().equals(userFolder.getId()));

    return new UserFolderIdResponse(userFolder.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public UserFolderListResponse findAll() {
    Account user = currentAccountProvider.get();

    List<UserFolder> userFolders = userFolderRepo.findAllByUserId(user.getId());

    List<UserFolderListResponse.UserFolderSummariseResponse> result = userFolders.stream()
            .map(userFolder -> new UserFolderListResponse.UserFolderSummariseResponse(
                    userFolder.getId(),
                    userFolder.getName()
            ))
            .toList();

    return new UserFolderListResponse(result);
  }

  @Override
  @Transactional(readOnly = true)
  public UserFolderListResponse findRoot() {
    Account user = currentAccountProvider.get();

    List<UserFolder> rootFolders = userFolderRepo.findAllRootByUserId(user.getId());

    List<UserFolderListResponse.UserFolderSummariseResponse> result = rootFolders.stream()
            .map(userFolder -> new UserFolderListResponse.UserFolderSummariseResponse(
                    userFolder.getId(),
                    userFolder.getName()
            ))
            .toList();

    return new UserFolderListResponse(result);
  }

  @Override
  @Transactional(readOnly = true)
  public UserFolderDetailResponse detailById(UUID id) {
    Account user = currentAccountProvider.get();

    UserFolder userFolder = userFolderRepo.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));

    Set<UserFolderDetailResponse.Children> children = new HashSet<>();

    userFolder.getChildren().forEach(folder -> children.add(
            new UserFolderDetailResponse.Children(folder.getId(), folder.getName(), true)
    ));

    userDeckRepo.findAllByUserFoldersIdAndUserId(userFolder.getId(), user.getId())
            .forEach(deck -> children.add(
                    new UserFolderDetailResponse.Children(deck.getId(), deck.getName(), false)
            ));

    return new UserFolderDetailResponse(
            userFolder.getId(),
            userFolder.getName(),
            userFolder.getFolderId(),
            children,
            userFolder.getCreatedAt(),
            userFolder.getUpdatedAt()
    );
  }

  @Override
  public void delete(UUID id) {
    Account user = currentAccountProvider.get();

    UserFolder userFolder = userFolderRepo.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));

    deleteFolderTree(user, userFolder, new HashSet<>());

    userGrammarService.deleteRedundant();

  }

  private void deleteFolderTree(Account user, UserFolder folder, Set<UUID> deletedIds) {
    UUID folderId = folder.getId();

    if (!deletedIds.add(folderId)) {
      return;
    }

    for (UserFolder parent : userFolderRepo.findAllByChildrenId(folderId)) {
      parent.getChildren().removeIf(c -> c.getId().equals(folderId));
    }

    List<UserFolder> childFolders = new ArrayList<>(folder.getChildren());
    folder.getChildren().clear();

    List<UserDeck> childDecks = userDeckRepo.findAllByUserFoldersIdAndUserId(folderId, user.getId());

    for (UserDeck deck : childDecks) {
      deck.getUserFolders().removeIf(f -> f.getId().equals(folderId));
    }

    userFolderRepo.delete(folder);
    userFolderRepo.flush();

    for (UserDeck deck : childDecks) {
      if (deck.getUserFolders().isEmpty()) {
        userDeckRepo.delete(deck);
      }
    }

    for (UserFolder child : childFolders) {
      if (!userFolderRepo.existsByChildrenId(child.getId())) {
        deleteFolderTree(user, child, deletedIds);
      }
    }
  }

  private UserFolder cloneFolderTree(Account user, Folder folder, String nameOverride, UserFolder parent) {
    UserFolderDuplicateCheckResponse dup = checkDuplicate(new UserFolderDuplicateCheckRequest(folder.getId()));

    if (dup.duplicated()) {
      UserFolder existing = userFolderRepo
              .findByIdAndUserId(dup.existingUserFolderId(), user.getId())
              .orElseThrow(() -> new ApiException(ErrorCode.USER_FOLDER_NOT_FOUND));

      if (parent != null) {
        linkChild(parent, existing);
      }

      return existing;
    }

    UserFolder created = userFolderRepo.save(
            UserFolder.builder()
                    .user(user)
                    .name(nameOverride != null ? nameOverride : folder.getName())
                    .folderId(folder.getId())
                    .syncedVersion(folder.getChildrenVersion())
                    .build()
    );

    if (parent != null) {
      linkChild(parent, created);
    }

    for (FolderHierarchy link : folder.getChildLinks()) {
      Folder childFolder = link.getChild();

      if (!Boolean.TRUE.equals(childFolder.getIsPublic())) {
        continue;
      }

      cloneFolderTree(user, childFolder, null, created);
    }

    for (Deck deck : findDecksOfFolder(folder)) {
      if (!Boolean.TRUE.equals(deck.getIsPublic())) {
        continue;
      }

      cloneDeckInto(user, deck, created);
    }

    return created;
  }

  private void cloneDeckInto(Account user, Deck deck, UserFolder targetFolder) {
    UserDeckDuplicateCheckResponse dup = userDeckService.checkDuplicate(
            new UserDeckDuplicateCheckRequest(deck.getId())
    );

    if (dup.duplicated()) {
      UserDeck existing = userDeckRepo
              .findByIdAndUserId(dup.existingUserDeckId(), user.getId())
              .orElseThrow(() -> new ApiException(ErrorCode.USER_DECK_NOT_FOUND));

      boolean alreadyInFolder = existing.getUserFolders().stream()
              .anyMatch(f -> f.getId().equals(targetFolder.getId()));

      if (!alreadyInFolder) {
        existing.getUserFolders().add(targetFolder);
      }

      return;
    }

    UserDeckIdResponse created = userDeckService.clone(
            new UserDeckCloneRequest(
                    deck.getId(),
                    deck.getName(),
                    targetFolder.getId(),
                    null,
                    null
            )
    );

    UserDeck userDeck = userDeckRepo
            .findByIdAndUserId(created.id(), user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_DECK_NOT_FOUND));

    userDeck.setSyncedVersion(deck.getChildrenVersion());

    cloneGrammarsOfDeck(userDeck, deck);
  }

  private void cloneGrammarsOfDeck(UserDeck userDeck, Deck deck) {
    List<UserGrammarCreateRequest> toCreate = new ArrayList<>();
    List<UUID> existingUserGrammarIds = new ArrayList<>();
    Set<UUID> seenGrammarIds = new HashSet<>();
    for (Grammar grammar : findGrammarsOfDeck(deck)) {
      if (!seenGrammarIds.add(grammar.getId())) {
        continue;
      }

      UserGrammarDuplicateCheckResponse dup = userGrammarService.duplicateCheck(
              new UserGrammarDuplicateCheckRequest(grammar.getId())
      );

      if (dup.duplicated()) {
        existingUserGrammarIds.add(dup.existingUserGrammarId());
      } else {
        toCreate.add(new UserGrammarCreateRequest(
                grammar.getId(),
                grammar.getTitle(),
                userDeck.getId(),
                null
        ));
      }
    }

    if (!toCreate.isEmpty()) {
      userGrammarService.createMultiple(new UserGrammarCreateMultipleRequest(toCreate));
    }

    if (!existingUserGrammarIds.isEmpty()) {
      userDeck.getUserGrammars().addAll(userGrammarRepo.findAllById(existingUserGrammarIds));
    }
  }

  private List<Deck> findDecksOfFolder(Folder folder) {
    List<FolderDeck> folderDecks = folderDeckRepo.findByFolderId(folder.getId());

    return folderDecks.stream()
            .map(FolderDeck::getDeck)
            .toList();
  }

  private List<Grammar> findGrammarsOfDeck(Deck deck) {
    List<DeckGrammar> deckGrammars = deckGrammarRepo.findByIdDeckId(deck.getId());

    return deckGrammars.stream()
            .map(DeckGrammar::getGrammar)
            .toList();
  }

  private void linkChild(UserFolder parent, UserFolder child) {
    boolean alreadyLinked = parent.getChildren().stream()
            .anyMatch(c -> c.getId().equals(child.getId()));

    if (alreadyLinked) {
      return;
    }

    if (isSelfOrDescendant(child, parent.getId())) {
      throw new ApiException(ErrorCode.USER_FOLDER_INVALID_HIERARCHY);
    }

    parent.getChildren().add(child);
  }

  private boolean isSelfOrDescendant(UserFolder root, UUID targetId) {
    Set<UUID> visited = new HashSet<>();
    Deque<UserFolder> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      UserFolder current = stack.pop();

      if (!visited.add(current.getId())) {
        continue;
      }

      if (current.getId().equals(targetId)) {
        return true;
      }

      stack.addAll(current.getChildren());
    }

    return false;
  }
}