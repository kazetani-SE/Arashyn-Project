package com.arashi.edu.arashynbe.features.hub.userdeck.service.impl;

import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.hub.UserDeck;
import com.arashi.edu.arashynbe.entity.hub.UserFolder;
import com.arashi.edu.arashynbe.entity.system.Deck;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckCreateRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckIdResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckListResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.service.UserDeckService;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckDetailResponse;
import com.arashi.edu.arashynbe.features.system.deck.service.DeckService;
import com.arashi.edu.arashynbe.repository.hub.UserDeckRepo;
import com.arashi.edu.arashynbe.repository.hub.UserFolderRepo;
import com.arashi.edu.arashynbe.repository.system.DeckRepo;
import com.arashi.edu.arashynbe.shared.currentaccount.CurrentAccountProvider;
import com.arashi.edu.arashynbe.shared.enums.Language;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDeckServiceImpl implements UserDeckService {

  private final DeckRepo deckRepo;
  private final UserDeckRepo  userDeckRepo;
  private final UserFolderRepo userFolderRepo;

  private final DeckService deckService;

  private final CurrentAccountProvider currentAccountProvider;

  @Override
  public UserDeckIdResponse create(UserDeckCreateRequest request) {
    Account user = currentAccountProvider.get();

    Deck deck = deckRepo.findById(request.deckId())
            .orElseThrow(() -> new ApiException(ErrorCode.DECK_NOT_FOUND));

    UserDeck.UserDeckBuilder builder = UserDeck.builder()
            .deck(deck)
            .user(user)
            .name(
                    request.name() != null && !request.name().isBlank()
                            ? request.name().trim()
                            : deck.getName()
            )
            .proficiency((short) 0)
            .lastOpenAt(null);

    if (request.sourceUserDeckId() != null) {

      UserDeck source = userDeckRepo
              .findByIdAndUserId(
                      request.sourceUserDeckId(),
                      user.getId()
              )
              .orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));

      builder
              .proficiency(source.getProficiency())
              .lastOpenAt(source.getLastOpenAt());
    }

    UserDeck userDeck = builder.build();

    if (request.userFolderId() != null) {

      UserFolder folder = userFolderRepo
              .findByIdAndUserId(
                      request.userFolderId(),
                      user.getId()
              )
              .orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));

      userDeck.getUserFolders().add(folder);
    }

    UserDeck saved = userDeckRepo.save(userDeck);

    return new UserDeckIdResponse(saved.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public UserDeckDuplicateCheckResponse checkDuplicate(
          UserDeckDuplicateCheckRequest request
  ) {
    Account user = currentAccountProvider.get();

    return userDeckRepo
            .findFirstByDeckIdAndUserId(
                    request.deckId(),
                    user.getId()
            )
            .map(userDeck -> new UserDeckDuplicateCheckResponse(
                    true,
                    userDeck.getId()
            ))
            .orElseGet(() -> new UserDeckDuplicateCheckResponse(
                    false,
                    null
            ));
  }

  @Transactional
  @Override
  public UserDeckIdResponse update(UserDeckUpdateRequest request) {
    Account user = currentAccountProvider.get();

    UserDeck userDeck = userDeckRepo.findByIdAndUserId(request.userDeckId(), user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));

    if (request.name() != null && !request.name().isBlank()) {
      userDeck.setName(request.name().trim());
    }

    UUID currentFolderId = request.currentUserFolderId();
    UUID newFolderId = request.newUserFolderId();

    if (currentFolderId == null && newFolderId == null) {
      return new UserDeckIdResponse(userDeck.getId());
    }

    if (currentFolderId == null) {
      UserFolder newFolder = userFolderRepo.findByIdAndUserId(newFolderId, user.getId())
              .orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));

      userDeck.getUserFolders().add(newFolder);
      return new UserDeckIdResponse(userDeck.getId());
    }

    boolean exists = userDeckRepo.existsByIdAndUserIdAndUserFoldersId(
            userDeck.getId(), user.getId(), currentFolderId);

    if (!exists) {
      throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    if (newFolderId == null) {
      userDeck.getUserFolders().removeIf(folder -> folder.getId().equals(currentFolderId));
      return new UserDeckIdResponse(userDeck.getId());
    }

    if (currentFolderId.equals(newFolderId)) {
      return new UserDeckIdResponse(userDeck.getId());
    }

    UserFolder newFolder = userFolderRepo.findByIdAndUserId(newFolderId, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));

    userDeck.getUserFolders().removeIf(folder -> folder.getId().equals(currentFolderId));
    userDeck.getUserFolders().add(newFolder);

    return new UserDeckIdResponse(userDeck.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public UserDeckListResponse findAll() {
    Account user = currentAccountProvider.get();

    List<UserDeck> userDecks = userDeckRepo.findAllByUserId(user.getId());

    List<UserDeckListResponse.UserDeckSummariseResponse> result = userDecks.stream()
            .map(userDeck -> {
              Deck deck = userDeck.getDeck();
              String name = userDeck.getName() != null ? userDeck.getName() : deck.getName();

              return new UserDeckListResponse.UserDeckSummariseResponse(
                      userDeck.getId(),
                      name,
                      deck.getDescription(),
                      Language.valueOf(deck.getLanguage())
              );
            })
            .toList();

    return new UserDeckListResponse(result);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDeckDetailResponse findById(UUID id) {
    Account user = currentAccountProvider.get();

    UserDeck userDeck = userDeckRepo.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));

    UUID deckId = userDeck.getDeck().getId();
    DeckDetailResponse deck = deckService.findDeckById(deckId);

    return new UserDeckDetailResponse(
            userDeck.getId(),
            deck.id(),
            userDeck.getName(),
            deck.description(),
            deck.language(),
            deck.grammars(),
            userDeck.getCreatedAt(),
            userDeck.getUpdatedAt()
    );
  }

  @Override
  public void delete(UUID id) {
    Account user = currentAccountProvider.get();

    UserDeck userDeck = userDeckRepo.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));

    userDeckRepo.delete(userDeck);
  }
}