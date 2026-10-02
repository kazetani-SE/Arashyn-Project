package com.arashi.edu.arashynbe.features.system.deck.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.hub.UserDeck;
import com.arashi.edu.arashynbe.entity.system.Deck;
import com.arashi.edu.arashynbe.entity.system.support.DeckGrammar;
import com.arashi.edu.arashynbe.entity.system.support.DeckGrammarId;
import com.arashi.edu.arashynbe.entity.system.Folder;
import com.arashi.edu.arashynbe.entity.system.support.FolderDeck;
import com.arashi.edu.arashynbe.entity.system.support.FolderDeckId;
import com.arashi.edu.arashynbe.entity.system.Grammar;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckAssignGrammarRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckCreateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckUpdateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckDetailResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckIdResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckListResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckCheckUpdateResponse;
import com.arashi.edu.arashynbe.features.system.deck.service.DeckService;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderListResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarSummaryResponse;
import com.arashi.edu.arashynbe.features.system.grammar.service.GrammarListReadService;
import com.arashi.edu.arashynbe.repository.hub.UserDeckRepo;
import com.arashi.edu.arashynbe.repository.hub.support.UserDeckSyncBaseRepo;
import com.arashi.edu.arashynbe.repository.system.DeckRepo;
import com.arashi.edu.arashynbe.repository.system.FolderRepo;
import com.arashi.edu.arashynbe.repository.system.GrammarRepo;
import com.arashi.edu.arashynbe.repository.system.support.DeckGrammarRepo;
import com.arashi.edu.arashynbe.repository.system.support.FolderDeckRepo;
import com.arashi.edu.arashynbe.shared.currentaccount.CurrentAccountProvider;
import com.arashi.edu.arashynbe.shared.enums.Language;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import com.arashi.edu.arashynbe.shared.ownership.OwnerShip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DeckServiceImpl implements DeckService {

  private final DeckRepo deckRepo;
  private final FolderRepo folderRepo;
  private final GrammarRepo grammarRepo;
  private final FolderDeckRepo folderDeckRepo;
  private final DeckGrammarRepo deckGrammarRepo;
  private final UserDeckRepo userDeckRepo;
  private final UserDeckSyncBaseRepo userDeckSyncBaseRepo;

  private final GrammarListReadService grammarListReadService;
  private final CurrentAccountProvider currentAccountProvider;

  private final OwnerShip ownerShip;

  @Override
  public DeckIdResponse createDeck(DeckCreateRequest request) {
    Account owner = currentAccountProvider.get();

    Deck deck = new Deck();
    deck.setName(request.name());
    deck.setDescription(request.description());
    deck.setLanguage(request.language().name());
    deck.setOwner(owner);
    deck.setIsPublic(Boolean.TRUE.equals(request.isPublic()));

    deck = deckRepo.save(deck);

    if (request.folderId() != null) {
      attachFolder(deck, request.folderId());
    }
    if (request.grammarIds() != null && !request.grammarIds().isEmpty()) {
      attachGrammars(deck, request.grammarIds());
    }

    return new DeckIdResponse(deck.getId());
  }

  @Override
  public DeckIdResponse updateDeck(DeckUpdateRequest request) {
    Deck deck = ownerShip.requireOwnership(request.id(), deckRepo, ErrorCode.DECK_NOT_FOUND);

    if (request.name() != null && !request.name().isBlank()) {
      deck.setName(request.name());
    }
    if (request.description() != null && !request.description().isBlank()) {
      deck.setDescription(request.description());
    }
    if (request.isPublic() != null) {
      deck.setIsPublic(request.isPublic());
    }

    changeFolder(deck, request.oldFolderId(), request.newFolderId());

    return new DeckIdResponse(deck.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public DeckListResponse listDecks() {
    List<DeckListResponse.DeckSummariseResponse> decks = deckRepo.findAllPublicWithOwner()
            .stream()
            .map(this::toSummariseResponse)
            .toList();

    return new DeckListResponse(decks);
  }

  @Override
  @Transactional(readOnly = true)
  public DeckDetailResponse findDeckById(UUID id) {
    Deck deck = deckRepo.findById(id)
            .filter(d -> Boolean.TRUE.equals(d.getIsPublic())
                    || (d.getOwner() != null && d.getOwner().getId().equals(CurrentUser.getId())))
            .orElseThrow(() -> new ApiException(ErrorCode.DECK_NOT_FOUND));

    return toDetailResponse(deck);
  }

  @Override
  public boolean hasReferences(UUID id) {
    return userDeckRepo.existsByDeckId(id);
  }

  @Override
  public void deleteDeck(UUID id) {
    Deck deck = ownerShip.requireOwnership(id, deckRepo, ErrorCode.DECK_NOT_FOUND);
    deckRepo.delete(deck);
  }

  @Override
  @Transactional(readOnly = true)
  public DeckCheckUpdateResponse checkDeckUpdate(UUID userDeckId) {
    UserDeck userDeck = userDeckRepo.findById(userDeckId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_DECK_NOT_FOUND));

    UUID sourceId = userDeck.getDeckId();
    if (sourceId == null) {
      return new DeckCheckUpdateResponse(false, Set.of(), Set.of());
    }

    Deck source = deckRepo.findById(sourceId).orElse(null);
    if (source == null) {
      return new DeckCheckUpdateResponse(false, Set.of(), Set.of());
    }

    if (source.getChildrenVersion() == userDeck.getSyncedVersion()) {
      return new DeckCheckUpdateResponse(false, Set.of(), Set.of());
    }

    Set<UUID> currentSource = deckGrammarRepo.findGrammarIdsByDeckId(sourceId);
    Set<UUID> base = new HashSet<>(userDeckSyncBaseRepo.findGrammarIdsByUserDeckId(userDeckId));

    Set<UUID> added = new HashSet<>(currentSource);
    added.removeAll(base);

    Set<UUID> removed = new HashSet<>(base);
    removed.removeAll(currentSource);

    return new DeckCheckUpdateResponse(!added.isEmpty() || !removed.isEmpty(), added, removed);
  }

  @Override
  public DeckIdResponse assignGrammars(DeckAssignGrammarRequest request) {
    Deck deck = ownerShip.requireOwnership(request.deckId(), deckRepo, ErrorCode.DECK_NOT_FOUND);

    Set<UUID> currentGrammarIds = deckGrammarRepo.findGrammarIdsByDeckId(deck.getId());

    Set<UUID> toAdd = new HashSet<>(request.grammarIds());
    toAdd.removeAll(currentGrammarIds);

    Set<UUID> toRemove = new HashSet<>(request.grammarIds());
    toRemove.retainAll(currentGrammarIds);

    if (!toRemove.isEmpty()) {
      deckGrammarRepo.deleteByIdDeckIdAndIdGrammarIdIn(deck.getId(), toRemove);
    }

    if (!toAdd.isEmpty()) {
      attachGrammars(deck, toAdd);
    }

    return new DeckIdResponse(deck.getId());
  }

  private void attachFolder(Deck deck, UUID folderId) {

    Folder folder = ownerShip.requireOwnership(folderId, folderRepo, ErrorCode.FOLDER_NOT_FOUND);

    folderDeckRepo.save(FolderDeck.builder()
            .id(new FolderDeckId(folder.getId(), deck.getId()))
            .folder(folder)
            .deck(deck)
            .build());
  }

  private void changeFolder(Deck deck, UUID oldFolderId, UUID newFolderId) {
    if (oldFolderId == null && newFolderId == null) return;
    if (oldFolderId != null && oldFolderId.equals(newFolderId)) return;

    UUID deckId = deck.getId();

    Folder newFolder = null;
    if (newFolderId != null) {
      newFolder = ownerShip.requireOwnership(newFolderId, folderRepo, ErrorCode.FOLDER_NOT_FOUND);
      if (folderDeckRepo.existsByIdFolderIdAndIdDeckId(newFolderId, deckId)) {
        throw new ApiException(ErrorCode.INVALID_REQUEST);
      }
    }

    if (oldFolderId != null && folderDeckRepo.deleteLink(oldFolderId, deckId) == 0) {
      throw new ApiException(ErrorCode.INVALID_REQUEST);
    }

    if (newFolder != null) {
      attachFolder(deck, newFolderId);
    }
  }

  private void attachGrammars(Deck deck, Set<UUID> grammarIds) {

    List<Grammar> grammars = loadGrammars(grammarIds);

    List<DeckGrammar> links = grammars.stream()
            .map(grammar -> DeckGrammar.builder()
                    .id(new DeckGrammarId(deck.getId(), grammar.getId()))
                    .deck(deck)
                    .grammar(grammar)
                    .build())
            .toList();

    deckGrammarRepo.saveAll(links);
  }

  private List<Grammar> loadGrammars(Set<UUID> ids) {

    if (ids == null || ids.isEmpty()) {
      return List.of();
    }

    List<Grammar> grammars = grammarRepo.findAllById(ids);

    if (grammars.size() != ids.size()) {
      throw new ApiException(ErrorCode.GRAMMAR_NOT_FOUND);
    }

    return grammars;
  }

  private DeckListResponse.DeckSummariseResponse toSummariseResponse(Deck deck) {
    var owner = deck.getOwner();

    return new DeckListResponse.DeckSummariseResponse(
            deck.getId(),
            deck.getName(),
            deck.getDescription(),
            Language.valueOf(deck.getLanguage()),
            owner.getId(),
            owner.getUsername()
    );
  }

  private DeckDetailResponse toDetailResponse(Deck deck) {

    Set<FolderListResponse.FolderSummariseResponse> folders = folderDeckRepo
            .findByIdDeckId(deck.getId())
            .stream()
            .map(FolderDeck::getFolder)
            .map(folder -> new FolderListResponse.FolderSummariseResponse(
                    folder.getId(),
                    folder.getName(),
                    folder.getOwner() != null
                            ? folder.getOwner().getId()
                            : null,
                    folder.getOwner() != null
                            ? folder.getOwner().getUsername()
                            : null
            ))
            .collect(Collectors.toSet());

    GrammarListResponse grammarList =
            grammarListReadService.getByDeckId(deck.getId());

    Set<GrammarSummaryResponse> grammars =
            new HashSet<>(grammarList.items());

    UUID ownerId = deck.getOwner() != null
            ? deck.getOwner().getId()
            : null;

    return new DeckDetailResponse(
            deck.getId(),
            deck.getName(),
            deck.getDescription(),
            Language.valueOf(deck.getLanguage()),
            ownerId,
            deck.getIsPublic(),
            folders,
            grammars,
            deck.getCreatedAt(),
            deck.getUpdatedAt()
    );
  }
}