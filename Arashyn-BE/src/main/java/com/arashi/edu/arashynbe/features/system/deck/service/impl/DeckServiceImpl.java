package com.arashi.edu.arashynbe.features.system.deck.service.impl;

import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.system.Deck;
import com.arashi.edu.arashynbe.entity.system.support.DeckGrammar;
import com.arashi.edu.arashynbe.entity.system.support.DeckGrammarId;
import com.arashi.edu.arashynbe.entity.system.Folder;
import com.arashi.edu.arashynbe.entity.system.support.FolderDeck;
import com.arashi.edu.arashynbe.entity.system.support.FolderDeckId;
import com.arashi.edu.arashynbe.entity.system.Grammar;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckCreateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckUpdateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckDetailResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckIdResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckListResponse;
import com.arashi.edu.arashynbe.features.system.deck.service.DeckService;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderSummaryResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarSummaryResponse;
import com.arashi.edu.arashynbe.features.system.grammar.service.GrammarListReadService;
import com.arashi.edu.arashynbe.repository.hub.UserDeckRepo;
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

  private final GrammarListReadService grammarListReadService;
  private final CurrentAccountProvider currentAccountProvider;

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

    OwnerShip ownerShip = new OwnerShip();

    Deck deck = ownerShip.requireOwnership(
            request.id(),
            deckRepo,
            ErrorCode.DECK_NOT_FOUND
    );

    deck.setName(request.name());
    deck.setDescription(request.description());
    deck.setLanguage(request.language().name());

    if (request.isPublic() != null) {
      deck.setIsPublic(request.isPublic());
    }

    /*
     * folderIds is a single UUID.
     *
     * null:
     *     keep current folder relationships.
     *
     * non-null:
     *     replace current folder relationships with the specified folder.
     */
    if (request.folderId() != null) {
      folderDeckRepo.deleteByIdDeckId(deck.getId());
      attachFolder(deck, request.folderId());
    }

    /*
     * grammarIds is a Set.
     *
     * null:
     *     keep current grammar relationships.
     *
     * empty:
     *     remove all grammar relationships.
     *
     * non-empty:
     *     replace current grammar relationships.
     */
    if (request.grammarIds() != null) {
      deckGrammarRepo.deleteByIdDeckId(deck.getId());

      if (!request.grammarIds().isEmpty()) {
        attachGrammars(deck, request.grammarIds());
      }
    }

    return new DeckIdResponse(deck.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public DeckListResponse listDecks() {

    List<DeckListResponse.DeckSummariseResponse> decks = deckRepo.findAll()
            .stream()
            .map(this::toSummariseResponse)
            .toList();

    return new DeckListResponse(decks);
  }

  @Override
  @Transactional(readOnly = true)
  public DeckDetailResponse findDeckById(UUID id) {

    Deck deck = deckRepo.findById(id)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.DECK_NOT_FOUND)
            );

    return toDetailResponse(deck);
  }

  @Override
  public boolean hasReferences(UUID id) {
    return userDeckRepo.existsByDeckId(id);
  }

  @Override
  public void deleteDeck(UUID id) {

    OwnerShip ownerShip = new OwnerShip();

    Deck deck = ownerShip.requireOwnership(
            id,
            deckRepo,
            ErrorCode.DECK_NOT_FOUND
    );

    deckRepo.deleteById(deck.getId());
  }

  private void attachFolder(Deck deck, UUID folderId) {

    Folder folder = folderRepo.findById(folderId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.FOLDER_NOT_FOUND)
            );

    FolderDeck folderDeck = FolderDeck.builder()
            .id(new FolderDeckId(folder.getId(), deck.getId()))
            .folder(folder)
            .deck(deck)
            .build();

    folderDeckRepo.save(folderDeck);
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

  private DeckListResponse.DeckSummariseResponse toSummariseResponse(
          Deck deck
  ) {
    UUID ownerId = deck.getOwner() != null
            ? deck.getOwner().getId()
            : null;

    return new DeckListResponse.DeckSummariseResponse(
            deck.getId(),
            deck.getName(),
            deck.getDescription(),
            Language.valueOf(deck.getLanguage()),
            ownerId
    );
  }

  private DeckDetailResponse toDetailResponse(Deck deck) {

    Set<FolderSummaryResponse> folders = folderDeckRepo
            .findByIdDeckId(deck.getId())
            .stream()
            .map(FolderDeck::getFolder)
            .map(folder -> new FolderSummaryResponse(
                    folder.getId(),
                    folder.getName(),
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